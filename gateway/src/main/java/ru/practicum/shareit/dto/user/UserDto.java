package ru.practicum.shareit.dto.user;

import lombok.Data;

// DTO для пользователя
@Data
public class UserDto {
    // Уникальный идентификатор пользователя
    private Long id;
    // Имя пользователя
    private String name;
    // Email пользователя
    private String email;
}
