package ru.practicum.shareit.dto.user;

import lombok.Data;

// DTO для обновления информации о пользователе
@Data
public class UpdateUserRequest {
    // Имя пользователя
    private String name;
    // Email пользователя
    private String email;

    // Проверяет наличие имени
    public boolean hasName() {
        return name != null && !name.isBlank();
    }

    // Проверяет наличие email
    public boolean hasEmail() {
        return email != null && !email.isBlank();
    }
}
