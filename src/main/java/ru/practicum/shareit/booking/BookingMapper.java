package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

// Маппер для преобразования сущности Booking в DTO
public class BookingMapper {
    // Преобразует сущность Booking в BookingDto
    public static BookingDto mapToBooking(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBooker(booking.getBooker());
        bookingDto.setItem(booking.getItem());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());

        return bookingDto;
    }
}
