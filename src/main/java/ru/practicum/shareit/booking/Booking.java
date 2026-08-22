package ru.practicum.shareit.booking;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность бронирования вещи
@Data
public class Booking {
    // Уникальный идентификатор бронирования
    private Long id;
    // Дата и время начала аренды
    private LocalDateTime start;
    // Дата и время окончания аренды
    private LocalDateTime end;
    // Арендуемая вещь
    private Item item;
    // Пользователь, бронирующий вещь
    private User booker;
    // Статус бронирования
    private BookingStatus status;
}