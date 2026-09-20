package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemRequestService itemRequestService;

    @Autowired
    private BookingService bookingService;

    @Test
    void addItem_ShouldSaveItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = createNewItemDto("Test Item", "Test Desc", true);

        Item saved = itemService.addItem(dto, user.getId());

        assertNotNull(saved.getId());
        assertEquals("Test Item", saved.getName());
    }

    @Test
    void updateItem_ShouldUpdateItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = createNewItemDto("Orig", "Orig", true);
        Item saved = itemService.addItem(dto, user.getId());

        UpdateItemRequest updateRequest = new UpdateItemRequest();
        updateRequest.setName("Updated");

        Item updated = itemService.updateItem(updateRequest, saved.getId(), user.getId());

        assertEquals("Updated", updated.getName());
    }

    @Test
    void getItem_ShouldReturnItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = createNewItemDto("Find Me", "Desc", true);
        Item saved = itemService.addItem(dto, user.getId());

        Item found = itemService.getItem(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void searchItem_ShouldReturnMatchingItems() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = createNewItemDto("Laptop", "Computer", true);
        itemService.addItem(dto, user.getId());

        var results = itemService.searchItem("LAPTOP");

        assertFalse(results.isEmpty());
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
    void searchItem_WithBlankText_ShouldReturnEmptyList() {
        var results = itemService.searchItem("   ");
        assertTrue(results.isEmpty());

        results = itemService.searchItem("");
        assertTrue(results.isEmpty());
    }

    @Test
    void addItem_WithNullName_ShouldThrowException() {
        User user = createUser("Owner", "owner@test.com");
        NewItemDto dto = new NewItemDto();
        dto.setName(null);
        dto.setDescription("Desc");
        dto.setAvailable(true);

        assertThrows(ValidationException.class, () -> {
            itemService.addItem(dto, user.getId());
        });
    }

    @Test
    void addItem_WithNullAvailable_ShouldThrowException() {
        User user = createUser("Owner", "owner@test.com");
        NewItemDto dto = new NewItemDto();
        dto.setName("Item");
        dto.setDescription("Desc");
        dto.setAvailable(null);

        assertThrows(ValidationException.class, () -> {
            itemService.addItem(dto, user.getId());
        });
    }

    @Test
    void getItem_NotFound_ShouldThrowException() {
        assertThrows(NotFoundException.class, () -> {
            itemService.getItem(9999L);
        });
    }

    @Test
    void getAllRequests_ShouldReturnAllRequests() {
        User user1 = createUser("User1", "user1@test.com");
        User user2 = createUser("User2", "user2@test.com");

        NewItemRequestDto requestDto1 = new NewItemRequestDto();
        requestDto1.setDescription("Request1");
        itemRequestService.addItemRequest(requestDto1, user1.getId());

        NewItemRequestDto requestDto2 = new NewItemRequestDto();
        requestDto2.setDescription("Request2");
        itemRequestService.addItemRequest(requestDto2, user2.getId());

        var requests = itemRequestService.getItemRequestItemAll();
        assertTrue(requests.size() >= 2);
    }

    @Test
    void getRequestById_WithItems_ShouldReturnRequestWithItems() {
        User requester = createUser("Requester", "requester@test.com");
        User owner = createUser("Owner", "owner@test.com");

        NewItemRequestDto requestDto = new NewItemRequestDto();
        requestDto.setDescription("Need laptop");
        ItemRequest request = itemRequestService.addItemRequest(requestDto, requester.getId());

        NewItemDto itemDto = createNewItemDto("Laptop", "MacBook", true);
        itemDto.setRequestId(request.getId());
        itemService.addItem(itemDto, owner.getId());

        ItemRequestDto found = itemRequestService.getRequestById(request.getId());

        assertNotNull(found);
        assertFalse(found.getItems().isEmpty());
    }

    @Test
    void getItemRequestItemAll_ShouldReturnAllRequests() {
        User user1 = createUser("User1", "user1@test.com");
        User user2 = createUser("User2", "user2@test.com");

        NewItemRequestDto requestDto1 = new NewItemRequestDto();
        requestDto1.setDescription("Request1");
        itemRequestService.addItemRequest(requestDto1, user1.getId());

        NewItemRequestDto requestDto2 = new NewItemRequestDto();
        requestDto2.setDescription("Request2");
        itemRequestService.addItemRequest(requestDto2, user2.getId());

        var requests = itemRequestService.getItemRequestItemAll();
        assertTrue(requests.size() >= 2);
    }

    @Test
    void addItem_WithBlankName_ShouldThrowException() {
        User user = createUser("Owner", "owner@test.com");
        NewItemDto dto = createNewItemDto("   ", "Desc", true);
        assertThrows(ValidationException.class, () -> itemService.addItem(dto, user.getId()));
    }

    @Test
    void addItem_WithBlankDescription_ShouldThrowException() {
        User user = createUser("Owner", "owner@test.com");
        NewItemDto dto = createNewItemDto("Name", "   ", true);
        assertThrows(ValidationException.class, () -> itemService.addItem(dto, user.getId()));
    }

    @Test
    void updateItem_ByNonOwner_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User other = createUser("Other", "other@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        UpdateItemRequest request = new UpdateItemRequest();
        request.setName("New Name");

        assertThrows(ValidationException.class, () -> {
            itemService.updateItem(request, item.getId(), other.getId());
        });
    }

    @Test
    void deleteItem_NotFound_ShouldThrowException() {
        assertThrows(NotFoundException.class, () -> {
            itemService.deleteItem(9999L);
        });
    }

    @Test
    void searchItem_WithNullText_ShouldReturnEmptyList() {
        var results = itemService.searchItem(null);
        assertTrue(results.isEmpty());
    }

    @Test
    void getItems_ShouldReturnUserItems() {
        User user = createUser("Owner", "owner@test.com");
        itemService.addItem(createNewItemDto("Item1", "Desc1", true), user.getId());
        itemService.addItem(createNewItemDto("Item2", "Desc2", true), user.getId());

        var items = itemService.getItems(user.getId());

        assertEquals(2, items.size());
    }

    @Test
    void deleteItem_ShouldRemoveItem() {
        User user = createUser("Owner", "owner@test.com");
        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), user.getId());

        itemService.deleteItem(item.getId());

        assertThrows(NotFoundException.class, () -> {
            itemService.getItem(item.getId());
        });
    }


    @Test
    void addComment_ShouldSaveComment() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");

        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusDays(2));
        bookingDto.setEnd(LocalDateTime.now().minusDays(1));

        Booking booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

            NewCommentDto commentDto = new NewCommentDto();
        commentDto.setText("Great item!");

        CommentDto comment = itemService.addComment(item.getId(), booker.getId(), commentDto);

        assertNotNull(comment.getId());
        assertEquals("Great item!", comment.getText());
    }

    @Test
    void updateItem_WithNullFields_ShouldNotChange() {
        User user = createUser("Owner", "owner@test.com");
        Item item = itemService.addItem(createNewItemDto("Original", "Desc", true), user.getId());

        UpdateItemRequest request = new UpdateItemRequest();
        // Все поля null

        Item updated = itemService.updateItem(request, item.getId(), user.getId());
        assertEquals("Original", updated.getName());
        assertEquals("Desc", updated.getDescription());
        assertTrue(updated.getAvailable());
    }

    @Test
    void getItems_EmptyList_ShouldReturnEmpty() {
        User user = createUser("Owner", "owner@test.com");
        var items = itemService.getItems(user.getId());
        assertTrue(items.isEmpty());
    }

    @Test
    void addItem_WithRequestId_ShouldLinkToRequest() {
        User owner = createUser("Owner", "owner@test.com");
        User requester = createUser("Requester", "requester@test.com");

        // Создаем запрос
        NewItemRequestDto requestDto = new NewItemRequestDto();
        requestDto.setDescription("Need laptop");
        ItemRequest request = itemRequestService.addItemRequest(requestDto, requester.getId());

        // Создаем вещь с привязкой к запросу
        NewItemDto itemDto = createNewItemDto("Laptop", "MacBook", true);
        itemDto.setRequestId(request.getId());

        Item saved = itemService.addItem(itemDto, owner.getId());

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
    }

    @Test
    void addComment_WithoutCompletedBooking_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");

        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        NewCommentDto commentDto = new NewCommentDto();
        commentDto.setText("Great!");

        assertThrows(ValidationException.class, () -> {
            itemService.addComment(item.getId(), booker.getId(), commentDto);
        });
    }

    @Test
    void addComment_WithFutureBooking_ShouldThrowException() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");

        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        // Создаем будущее бронирование (еще не завершилось)
        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().plusDays(1));
        bookingDto.setEnd(LocalDateTime.now().plusDays(2));

        bookingService.addBooking(bookingDto, booker.getId());

        NewCommentDto commentDto = new NewCommentDto();
        commentDto.setText("Great!");

        assertThrows(ValidationException.class, () -> {
            itemService.addComment(item.getId(), booker.getId(), commentDto);
        });
    }

    @Test
    void getOwnerItems_WithBookings_ShouldReturnItemsWithBookingInfo() {
        User owner = createUser("Owner", "owner@test.com");
        User booker = createUser("Booker", "booker@test.com");

        Item item = itemService.addItem(createNewItemDto("Item", "Desc", true), owner.getId());

        // Создаем прошедшее бронирование
        NewBookingDto pastBookingDto = new NewBookingDto();
        pastBookingDto.setItemId(item.getId());
        pastBookingDto.setStart(LocalDateTime.now().minusDays(2));
        pastBookingDto.setEnd(LocalDateTime.now().minusDays(1));

        Booking pastBooking = bookingService.addBooking(pastBookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), pastBooking.getId(), true);

        // Создаем будущее бронирование
        NewBookingDto futureBookingDto = new NewBookingDto();
        futureBookingDto.setItemId(item.getId());
        futureBookingDto.setStart(LocalDateTime.now().plusDays(1));
        futureBookingDto.setEnd(LocalDateTime.now().plusDays(2));

        Booking futureBooking = bookingService.addBooking(futureBookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), futureBooking.getId(), true);

        var items = itemService.getOwnerItems(owner.getId());

        assertFalse(items.isEmpty());
        // Проверяем, что lastBooking и nextBooking установлены
        var itemDto = items.get(0);
        assertNotNull(itemDto.getLastBooking());
        assertNotNull(itemDto.getNextBooking());
    }

}