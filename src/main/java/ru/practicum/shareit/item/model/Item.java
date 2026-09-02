package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

// Сущность вещи для аренды
@Data
public class Item {
    // Уникальный идентификатор вещи
    private Long id;
    // Название вещи
    @NotNull(message = "Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    // Описание вещи
    @NotNull(message = "Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    private String description;
    // Доступность вещи для аренды
    private Boolean available;
    // Владелец вещи
    private User owner;
    // Запрос на бронирование вещи
    private ItemRequest request;
}
