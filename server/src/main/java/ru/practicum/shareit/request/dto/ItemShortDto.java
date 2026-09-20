package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO для краткой информации о вещи
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemShortDto {
    // Уникальный идентификатор вещи
    private Long id;
    // Название вещи
    private String name;
    // ID владельца вещи
    private Long ownerId;
}