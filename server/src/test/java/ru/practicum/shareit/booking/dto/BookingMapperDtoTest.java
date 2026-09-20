package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingMapperDtoTest {

    @Test
    void mapToBooking_ShouldMapCorrectly() {
        User user = new User();
        user.setId(1L);

        Item item = new Item();
        item.setId(2L);

        NewBookingDto dto = new NewBookingDto();
        dto.setItemId(2L);
        dto.setStart(LocalDateTime.now());
        dto.setEnd(LocalDateTime.now().plusDays(1));

        Booking booking = BookingMapperDto.mapToBooking(dto, user, item);

        assertNotNull(booking);
        assertEquals(user, booking.getBooker());
        assertEquals(item, booking.getItem());
        assertEquals(dto.getStart(), booking.getStart());
        assertEquals(dto.getEnd(), booking.getEnd());
    }

    @Test
    void toBookingShortDto_ShouldMapCorrectly() {
        User booker = new User();
        booker.setId(1L);

        Booking booking = new Booking();
        booking.setId(3L);
        booking.setBooker(booker);
        booking.setStart(LocalDateTime.now());
        booking.setEnd(LocalDateTime.now().plusDays(1));

        BookingShortDto dto = BookingMapperDto.toBookingShortDto(booking);

        assertNotNull(dto);
        assertEquals(3L, dto.getId());
        assertEquals(1L, dto.getBookerId());
        assertEquals(booking.getStart(), dto.getStart());
        assertEquals(booking.getEnd(), dto.getEnd());
    }
}
