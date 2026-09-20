package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

// Маппер для преобразования сущности Comment в DTO
public class CommentMapper {
    // Преобразует сущность Comment в CommentDto
    public static CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setAuthorName(comment.getAuthor().getName());
        commentDto.setCreated(comment.getCreated());
        return commentDto;
    }

    // Преобразует коллекцию комментариев в DTO
    public static Collection<CommentDto> mapToDto(Collection<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::mapToDto)
                .toList();
    }
}
