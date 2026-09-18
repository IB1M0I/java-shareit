package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO для создания нового комментария
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    // Текст комментария
    private String text;
}