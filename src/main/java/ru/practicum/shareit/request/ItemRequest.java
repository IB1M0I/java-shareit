package ru.practicum.shareit.request;

import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность запроса на бронирование вещи
@Data
public class ItemRequest {
    // Уникальный идентификатор запроса
    private Long id;
    // Описание запроса
    private String description;
    // Пользователь, создавший запрос
    private User requester;
    // Дата и время создания запроса
    private LocalDateTime created;
}
