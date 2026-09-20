package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.NewBookingDto;

import java.util.Collection;

// REST контроллер для управления бронированиями
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    // Сервис для работы с бронированиями
    private final BookingService bookingService;

    // Создает новое бронирование
    @PostMapping
    public Booking addBooking(@RequestBody NewBookingDto newBooking, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.addBooking(newBooking, userId);
    }

    // Подтверждает или отклоняет бронирование
    @PatchMapping("/{bookingId}")
    public Booking approvedBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long bookingId, @RequestParam boolean approved) {
        return bookingService.approvedBooking(userId, bookingId, approved);
    }

    // Получает бронирование по ID
    @GetMapping("/{bookingId}")
    public Booking getBookingId(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return bookingService.getBookingId(bookingId, userId);
    }

    // Получает все бронирования пользователя с фильтрацией по состоянию
    @GetMapping
    public Collection<Booking> getAllBookingBooker(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(value = "state", defaultValue = "ALL") String state) {
        return bookingService.getAllBookingBooker(userId, state);
    }

    // Получает все бронирования вещей владельца с фильтрацией по состоянию
    @GetMapping("/owner")
    public Collection<Booking> getAllBookingOwner(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestParam(value = "state", defaultValue = "ALL") String state) {
        return bookingService.getAllBookingOwner(userId, state);
    }
}
