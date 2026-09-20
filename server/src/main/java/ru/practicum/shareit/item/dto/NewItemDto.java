package ru.practicum.shareit.item.dto;

import lombok.Data;

// DTO для создания новой вещи
@Data
public class NewItemDto {
    // Название вещи
    private String name;
    // Описание вещи
    private String description;
    // Доступность вещи для аренды
    private Boolean available;
    // ID запроса на вещь (опционально)
    private Long requestId;
}
