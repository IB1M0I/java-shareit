package ru.practicum.shareit.dto.item;

import lombok.Data;


// Запрос на обновление информации о вещи
@Data
public class UpdateItemRequest {
    // Название вещи
    private String name;
    // Описание вещи
    private String description;
    // Доступность вещи для аренды
    private Boolean available;

    // Проверяет наличие названия
    public boolean hasName() {
        return !(name == null || name.isBlank());
    }

    // Проверяет наличие описания
    public boolean hasDescription() {
        return !(description == null || description.isBlank());
    }

    // Проверяет наличие статуса доступности
    public boolean hasAvailable() {
        return available != null;
    }



}
