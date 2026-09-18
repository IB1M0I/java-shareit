package ru.practicum.shareit.booking.dto;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// DTO для передачи данных о бронировании
@Data
public class BookingDto {
    // Уникальный идентификатор бронирования
    private Long id;
    // Дата и время начала аренды
    private LocalDateTime start;
    // Дата и время окончания аренды
    private LocalDateTime end;
    // Статус бронирования
    private String status;
    // Арендуемая вещь
    private Item item;
    // Пользователь, бронирующий вещь
    private User booker;
}
