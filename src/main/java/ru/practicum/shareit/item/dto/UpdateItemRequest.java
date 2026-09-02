package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

// Запрос на обновление информации о вещи
@Data
public class UpdateItemRequest {
    // Название вещи
    private String name;
    // Описание вещи
    private String description;
    // Доступность вещи для аренды
    private Boolean available;
    // Владелец вещи
    private User owner;
    // Запрос на бронирование вещи
    private ItemRequest request;

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

    // Проверяет наличие владельца
    public boolean hasOwner() {
        return owner != null;
    }

    // Проверяет наличие запроса на бронирование
    public boolean hasRequest() {
        return request != null;
    }

}
