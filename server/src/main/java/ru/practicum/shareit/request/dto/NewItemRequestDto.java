package ru.practicum.shareit.request.dto;

import lombok.Getter;
import lombok.Setter;

// DTO для создания нового запроса на вещь
@Getter
@Setter
public class NewItemRequestDto {
    // Описание запрашиваемой вещи
    private String description;
}
