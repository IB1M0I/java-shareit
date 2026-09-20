package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Test
    void addBooking_ShouldSaveBooking() {
        User owner = createUser("Owner", "owner@example.com");
        User booker = createUser("Booker", "booker@example.com");

        NewItemDto itemDto = createNewItemDto("Item", "Desc", true);
        Item item = itemService.addItem(itemDto, owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking saved = bookingService.addBooking(bookingDto, booker.getId());

        assertNotNull(saved.getId());
        assertEquals(BookingStatus.WAITING, saved.getStatus());
    }

    @Test
    void approvedBooking_ShouldUpdateStatus() {
        User owner = createUser("Owner", "owner@example.com");
        User booker = createUser("Booker", "booker@example.com");

        NewItemDto itemDto = createNewItemDto("Item", "Desc", true);
        Item item = itemService.addItem(itemDto, owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking saved = bookingService.addBooking(bookingDto, booker.getId());
        Booking approved = bookingService.approvedBooking(owner.getId(), saved.getId(), true);

        assertEquals(BookingStatus.APPROVED, approved.getStatus());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.addUser(user);
    }

    private NewItemDto createNewItemDto(String name, String description, Boolean available) {
        NewItemDto dto = new NewItemDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setAvailable(available);
        return dto;
    }

    @Test
    void addBooking_ForNotAvailableItem_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", false), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        assertThrows(ValidationException.class, () -> {
            bookingService.addBooking(bookingDto, booker.getId());
        });
    }

    @Test
    void addBooking_ForOwnItem_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        assertThrows(ValidationException.class, () -> {
            bookingService.addBooking(bookingDto, owner.getId());
        });
    }

    @Test
    void approvedBooking_ByNonOwner_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        User other = createUser("Other", "other@test.com");

        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());

        assertThrows(ForbiddenException.class, () -> {
            bookingService.approvedBooking(other.getId(), booking.getId(), true);
        });
    }

    @Test
    void getBooking_NotFound_ShouldThrowException() {
        assertThrows(NotFoundException.class, () -> {
            bookingService.getBookingId(9999L, 1L);
        });
    }

    @Test
    void getAllBookingBooker_WithStateWaiting() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        bookingService.addBooking(bookingDto, booker.getId());

        var bookings = bookingService.getAllBookingBooker(booker.getId(), "WAITING");

        assertFalse(bookings.isEmpty());
        assertEquals(BookingStatus.WAITING, bookings.iterator().next().getStatus());
    }

    @Test
    void getAllBookingBooker_WithStatePast() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusDays(2));
        bookingDto.setEnd(LocalDateTime.now().minusDays(1));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        var bookings = bookingService.getAllBookingBooker(booker.getId(), "PAST");

        assertFalse(bookings.isEmpty());
    }

    @Test
    void approvedBooking_AlreadyApproved_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        assertThrows(ValidationException.class, () -> {
            bookingService.approvedBooking(owner.getId(), booking.getId(), true);
        });
    }

    @Test
    void addBooking_StartAfterEnd_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(2)); // Start ПОСЛЕ End
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));

        assertThrows(ValidationException.class, () -> {
            bookingService.addBooking(bookingDto, booker.getId());
        });
    }

    @Test
    void getBookingId_ByNonOwnerAndNonBooker_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        User other = createUser("Other", "other@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());

        assertThrows(ForbiddenException.class, () -> {
            bookingService.getBookingId(booking.getId(), other.getId());
        });
    }

    @Test
    void getAllBookingBooker_WithStateRejected() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), false); // Отклоняем

        var bookings = bookingService.getAllBookingBooker(booker.getId(), "REJECTED");
        assertFalse(bookings.isEmpty());
        assertEquals(BookingStatus.REJECTED, bookings.iterator().next().getStatus());
    }

    @Test
    void getAllBookingOwner_WithStateAll() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        bookingService.addBooking(bookingDto, booker.getId());

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "ALL");
        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingOwner_WithStateWaiting() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        bookingService.addBooking(bookingDto, booker.getId());

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "WAITING");

        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingOwner_WithStatePast() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusDays(2));
        bookingDto.setEnd(LocalDateTime.now().minusDays(1));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "PAST");

        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingBooker_WithStateCurrent() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusHours(1));
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        var bookings = bookingService.getAllBookingBooker(booker.getId(), "CURRENT");
        assertFalse(bookings.isEmpty());
    }



    @Test
    void approvedBooking_WithRejectedStatus_ShouldWork() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        Booking rejected = bookingService.approvedBooking(owner.getId(), booking.getId(), false);

        assertEquals(BookingStatus.REJECTED, rejected.getStatus());
    }



    @Test
    void getAllBookingBooker_WithStateFuture() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(10));
        bookingDto.setEnd(LocalDateTime.now().plusDays(11));

        bookingService.addBooking(bookingDto, booker.getId());

        var bookings = bookingService.getAllBookingBooker(booker.getId(), "FUTURE");
        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingOwner_WithStateRejected() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), false); // Отклоняем

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "REJECTED");
        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingOwner_WithStateCurrent() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusHours(1));
        bookingDto.setEnd(LocalDateTime.now().plusHours(1));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "CURRENT");
        assertFalse(bookings.isEmpty());
    }

    @Test
    void getAllBookingOwner_WithStateFuture() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(10));
        bookingDto.setEnd(LocalDateTime.now().plusDays(11));

        bookingService.addBooking(bookingDto, booker.getId());

        var bookings = bookingService.getAllBookingOwner(owner.getId(), "FUTURE");
        assertFalse(bookings.isEmpty());
    }

    @Test
    void addBooking_WithNullStart_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(null);
        bookingDto.setEnd(LocalDateTime.now().plusDays(1));

        assertThrows(ValidationException.class, () -> {
            bookingService.addBooking(bookingDto, booker.getId());
        });
    }

    @Test
    void addBooking_WithNullEnd_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(null);

        assertThrows(ValidationException.class, () -> {
            bookingService.addBooking(bookingDto, booker.getId());
        });
    }

}