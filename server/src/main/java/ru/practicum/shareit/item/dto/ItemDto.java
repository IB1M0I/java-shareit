package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.util.Collection;

// DTO для передачи данных о вещи
@Data
public class ItemDto {
    // Уникальный идентификатор вещи
    private Long id;
    // Название вещи
    private String name;
    // Описание вещи
    private String description;
    // Доступность вещи для аренды
    private Boolean available;
    // Владелец вещи
    private User owner;
    // Запрос на бронирование вещи
    private ItemRequest request;
    // Комментарии к вещи
    private Collection<CommentDto> comments;
    // Последнее завершенное бронирование
    private BookingShortDto lastBooking;
    // Следующее предстоящее бронирование
    private BookingShortDto nextBooking;
}
