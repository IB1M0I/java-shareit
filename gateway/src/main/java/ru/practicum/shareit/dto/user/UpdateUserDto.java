package ru.practicum.shareit.dto.user;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO для обновления информации о пользователе
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String name;

    @Email(message = "Email должен быть валидным")
    private String email;
}
