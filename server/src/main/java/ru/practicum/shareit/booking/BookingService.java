package ru.practicum.shareit.booking;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingMapperDto;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// Сервис для работы с бронированиями вещей
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    // Репозиторий для работы с бронированиями
    private final BookingRepository bookingRepository;
    // Репозиторий для работы с вещами
    private final ItemRepository itemRepository;
    // Репозиторий для работы с пользователями
    private final UserRepository userRepository;

    // Создает новое бронирование вещи
    @Transactional
    public Booking addBooking(NewBookingDto newBooking, Long userId) {
        Item item = itemRepository.findById(newBooking.getItemId())
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (newBooking.getStart() == null) {
            throw new ValidationException("Start не может быть null");
        }
        if (newBooking.getEnd() == null) {
            throw new ValidationException("End не может быть null");
        }
        if (!newBooking.getStart().isBefore(newBooking.getEnd())) {
            throw new ValidationException("Start должен быть меньше End");
        }
        if (!item.getAvailable()) {
            throw new ValidationException("Вещь недоступна для бронирования");
        }
        if (item.getOwner().getId().equals(userId)) {
            throw new ValidationException("Нельзя бронировать свою вещь");
        }
        Booking booking = BookingMapperDto.mapToBooking(newBooking, user, item);
        booking.setStatus(BookingStatus.WAITING);
        return bookingRepository.save(booking);
    }

    // Подтверждает или отклоняет бронирование (только владелец вещи)
    @Transactional
    public Booking approvedBooking(Long userId, Long bookingId, boolean approved) {
        Booking booking = bookingRepository.findByIdWithItem(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        Item item = booking.getItem();

        if (userId.equals(item.getOwner().getId())) {
            if (booking.getStatus() != BookingStatus.WAITING) {
                throw new ValidationException("Можно подтвердить только бронирование со статусом WAITING");
            }
            if (approved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
        } else {
            throw new ForbiddenException("Только владелец вещи может подтверждать бронирование");
        }
        return bookingRepository.save(booking);
    }

    // Получает бронирование по ID (доступно для владельца вещи или бронирующего)
    public Booking getBookingId(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        if (booking.getBooker().getId().equals(userId) || booking.getItem().getOwner().getId().equals(userId)) {
            return booking;
        }
        throw new ForbiddenException("Нет доступа к бронированию");
    }

    // Получает все бронирования пользователя с фильтрацией по состоянию
    public Collection<Booking> getAllBookingBooker(Long userId, String state) {
        List<Booking> bookings = switch (state) {
            case "ALL" -> bookingRepository.findByBookerId(userId);
            case "WAITING" -> bookingRepository.findByBookerIdAndStatus(userId, BookingStatus.WAITING);
            case "REJECTED" -> bookingRepository.findByBookerIdAndStatus(userId, BookingStatus.REJECTED);
            case "PAST" -> bookingRepository.findPastByBookerId(userId);
            case "CURRENT" -> bookingRepository.findCurrentByBookerId(userId);
            case "FUTURE" -> bookingRepository.findFutureByBookerId(userId);
            default -> throw new IllegalArgumentException("Неизвестный state: " + state);
        };

        // Сортировка от новых к старым (по дате start)
        bookings.sort(Comparator.comparing(Booking::getStart).reversed());

        return bookings;
    }

    // Получает все бронирования вещей владельца с фильтрацией по состоянию
    public Collection<Booking> getAllBookingOwner(Long userId, String state) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }
        List<Booking> bookings = switch (state) {
            case "ALL" -> bookingRepository.findByItemOwnerId(userId);
            case "WAITING" -> bookingRepository.findByItemOwnerIdAndStatus(userId, BookingStatus.WAITING);
            case "REJECTED" -> bookingRepository.findByItemOwnerIdAndStatus(userId, BookingStatus.REJECTED);
            case "PAST" -> bookingRepository.findPastByItemOwnerId(userId);
            case "CURRENT" -> bookingRepository.findCurrentByItemOwnerId(userId);
            case "FUTURE" -> bookingRepository.findFutureByItemOwnerId(userId);
            default -> throw new IllegalArgumentException("Неизвестный state: " + state);
        };

        // Сортировка от новых к старым
        bookings.sort(Comparator.comparing(Booking::getStart).reversed());

        return bookings;
    }
}
