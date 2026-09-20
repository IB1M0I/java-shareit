package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperTest {

    @Test
    void mapToBooking_ShouldMapCorrectly() {
        User booker = new User();
        booker.setId(1L);
        Item item = new Item();
        item.setId(2L);

        Booking booking = new Booking();
        booking.setId(3L);
        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));

        BookingDto dto = BookingMapper.mapToBooking(booking);

        assertEquals(3L, dto.getId());
        assertEquals(1L, dto.getBooker().getId());
        assertEquals(2L, dto.getItem().getId());
    }

    @Test
    void mapToBookingShort_ShouldMapCorrectly() {
        User booker = new User();
        booker.setId(1L);

        Booking booking = new Booking();
        booking.setId(3L);
        booking.setBooker(booker);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));

        BookingShortDto dto = BookingMapper.mapToBookingShort(booking);

        assertEquals(3L, dto.getId());
        assertEquals(1L, dto.getBookerId());
    }
}