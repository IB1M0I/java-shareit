package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.NewItemDto;
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

        NewItemDto itemDto = new NewItemDto();
        itemDto.setName("Item");
        itemDto.setDescription("Desc");
        itemDto.setAvailable(true);
        var item = itemService.addItem(itemDto, owner.getId());

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

        NewItemDto itemDto = new NewItemDto();
        itemDto.setName("Item");
        itemDto.setDescription("Desc");
        itemDto.setAvailable(true);
        var item = itemService.addItem(itemDto, owner.getId());

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
}