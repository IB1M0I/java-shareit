package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// DTO для передачи данных о запросе на вещь
@Data
public class ItemRequestDto {
    // Уникальный идентификатор запроса
    private Long id;
    // Описание запрашиваемой вещи
    private String description;
    // Пользователь, создавший запрос
    private User requester;
    // Дата и время создания запроса
    private LocalDateTime created;
}
