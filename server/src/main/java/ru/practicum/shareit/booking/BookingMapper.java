package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingShortDto;

// Маппер для преобразования сущности Booking в DTO
public class BookingMapper {
    // Преобразует сущность Booking в BookingDto
    public static BookingDto mapToBooking(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        BookingDto.Booker booker = new BookingDto.Booker(
                booking.getBooker().getId(),
                booking.getBooker().getName(),
                booking.getBooker().getEmail()
        );
        bookingDto.setBooker(booker);
        BookingDto.Item item = new BookingDto.Item(
                booking.getItem().getId(),
                booking.getItem().getName()
        );
        bookingDto.setItem(item);
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());
        bookingDto.setStatus(booking.getStatus().name());

        return bookingDto;
    }

    // Преобразует сущность Booking в BookingShortDto (краткая информация)
    public static BookingShortDto mapToBookingShort(Booking booking) {
        BookingShortDto bookingShortDto = new BookingShortDto();
        bookingShortDto.setId(booking.getId());
        bookingShortDto.setBookerId(booking.getBooker().getId());
        bookingShortDto.setStart(booking.getStart());
        bookingShortDto.setEnd(booking.getEnd());
        return bookingShortDto;
    }
}
