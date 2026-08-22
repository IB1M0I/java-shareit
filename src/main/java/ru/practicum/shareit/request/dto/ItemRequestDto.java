package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// DTO для запроса на бронирование вещи
@Data
public class ItemRequestDto {
    // Описание запроса
    private String description;
    // Пользователь, создавший запрос
    private User requester;
    // Дата и время создания запроса
    private LocalDateTime created;
}
