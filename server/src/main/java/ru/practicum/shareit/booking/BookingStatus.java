package ru.practicum.shareit.booking;

// Статусы бронирования вещи
public enum BookingStatus {
    // Ожидает подтверждения
    WAITING,
    // Подтверждено
    APPROVED,
    // Отклонено
    REJECTED,
    // Отменено
    CANCELED
}
