package ru.practicum.shareit.dto.request;

import lombok.Data;

// DTO для краткой информации о вещи в запросе
@Data
public class ItemShortDto {
    // Уникальный идентификатор вещи
    private Long id;
    // Название вещи
    private String name;
    // Идентификатор владельца
    private Long ownerId;
}
