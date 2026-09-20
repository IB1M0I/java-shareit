package ru.practicum.shareit.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

// DTO для запроса на вещь
@Data
public class ItemRequest {
    // Уникальный идентификатор запроса
    private Long id;
    // Описание запрашиваемой вещи
    private String description;
    // Пользователь, создавший запрос
    private Long requester;
    // Дата и время создания запроса
    private LocalDateTime created;
}
