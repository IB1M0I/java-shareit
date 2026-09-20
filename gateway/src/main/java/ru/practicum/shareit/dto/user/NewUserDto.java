package ru.practicum.shareit.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO для создания нового пользователя
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть валидным")
    private String email;
}
