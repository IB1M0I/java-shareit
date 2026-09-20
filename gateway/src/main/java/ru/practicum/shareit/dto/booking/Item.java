package ru.practicum.shareit.dto.booking;

import lombok.Data;

// DTO для вещи в бронировании
@Data
public class Item {
    // Уникальный идентификатор вещи
    private Long id;
    // Название вещи
    private String name;
}
