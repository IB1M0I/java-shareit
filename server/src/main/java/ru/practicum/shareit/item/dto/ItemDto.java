package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

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
    private UserDto owner;
    // Запрос на бронирование вещи
    private ItemRequestDto request;
    // Комментарии к вещи
    private Collection<CommentDto> comments;
    // Последнее завершенное бронирование
    private BookingShortDto lastBooking;
    // Следующее предстоящее бронирование
    private BookingShortDto nextBooking;
}
