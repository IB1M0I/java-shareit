package ru.practicum.shareit.request.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

// DTO для передачи данных о запросе на вещь
@Data
public class ItemRequestDto {
    // Уникальный идентификатор запроса
    private Long id;
    // Описание запрашиваемой вещи
    private String description;
    // Пользователь, создавший запрос
    private Long userId;
    // Дата и время создания запроса
    private LocalDateTime created;
    // Список вещей, предложенных в ответ на запрос
    private List<ItemShortDto> items;
}
