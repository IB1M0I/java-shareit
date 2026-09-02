package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Сущность пользователя
@Data
public class User {
    // Уникальный идентификатор пользователя
    private Long id;
    // Имя пользователя
    @NotNull(message = "Имя не может быть пустым")
    private String name;
    // Email пользователя
    @NotNull(message = "Email не может быть пустым")
    @Email(message = "Email не валидный")
    private String email;
}
