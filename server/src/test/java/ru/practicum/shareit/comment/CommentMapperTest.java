package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    @Test
    void mapToDto_ShouldMapCorrectly() {
        User author = new User();
        author.setId(1L);
        author.setName("John");

        Item item = new Item();
        item.setId(2L);

        Comment comment = new Comment();
        comment.setId(3L);
        comment.setText("Great!");
        comment.setAuthor(author);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());

        CommentDto dto = CommentMapper.mapToDto(comment);

        assertEquals(3L, dto.getId());
        assertEquals("Great!", dto.getText());
        assertEquals("John", dto.getAuthorName());
    }
}