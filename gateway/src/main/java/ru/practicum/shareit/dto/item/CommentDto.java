package ru.practicum.shareit.dto.item;

import lombok.Data;

import java.time.LocalDateTime;

// DTO для передачи данных о комментарии
@Data
public class CommentDto {
    // Уникальный идентификатор комментария
    Long id;
    // Текст комментария
    private String text;
    // Имя автора комментария
    private String authorName;
    // Дата и время создания комментария
    private LocalDateTime created;
}
