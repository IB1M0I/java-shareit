package ru.practicum.shareit.dto.item;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO для создания нового комментария
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    // Текст комментария
    @NotBlank(message = "Текст комментария не может быть пустым")
    private String text;
}