package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ModelAndDtoTest {

    @Test
    void item_ShouldCreateAndGetFields() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test Item");
        item.setDescription("Test Description");
        item.setAvailable(true);

        assertEquals(1L, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals("Test Description", item.getDescription());
        assertTrue(item.getAvailable());
    }

    @Test
    void comment_ShouldCreateAndGetFields() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Great comment");

        assertEquals(1L, comment.getId());
        assertEquals("Great comment", comment.getText());
    }

    @Test
    void user_ShouldCreateAndGetFields() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");

        assertEquals(1L, user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void itemRequest_ShouldCreateAndGetFields() {
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        request.setDescription("Need laptop");
        request.setCreated(LocalDateTime.now());

        assertEquals(1L, request.getId());
        assertEquals("Need laptop", request.getDescription());
        assertNotNull(request.getCreated());
    }

    @Test
    void booking_ShouldCreateAndGetFields() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStatus(BookingStatus.WAITING);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));

        assertEquals(1L, booking.getId());
        assertEquals(BookingStatus.WAITING, booking.getStatus());
        assertNotNull(booking.getStart());
        assertNotNull(booking.getEnd());
    }

    @Test
    void bookingStatus_ShouldHaveCorrectValues() {
        assertEquals(BookingStatus.WAITING, BookingStatus.valueOf("WAITING"));
        assertEquals(BookingStatus.APPROVED, BookingStatus.valueOf("APPROVED"));
        assertEquals(BookingStatus.REJECTED, BookingStatus.valueOf("REJECTED"));
    }
}