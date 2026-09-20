package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    @Test
    void mapToDto_ShouldMapCorrectlyWithoutComments() {
        User owner = new User();
        owner.setId(1L);

        Item item = new Item();
        item.setId(2L);
        item.setName("Test");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwner(owner);
        // Комментарии null, чтобы проверить ветку if (item.getComments() != null)

        ItemDto dto = ItemMapper.mapToDto(item);

        assertEquals(2L, dto.getId());
        assertEquals("Test", dto.getName());
        assertEquals("Desc", dto.getDescription());
        assertTrue(dto.getAvailable());
        assertEquals(1L, dto.getOwner().getId());
        assertNull(dto.getComments());
    }

    @Test
    void mapToDto_ShouldMapCorrectlyWithComments() {
        User owner = new User();
        owner.setId(1L);
        owner.setName("OwnerName");

        Item item = new Item();
        item.setId(2L);
        item.setName("Test");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwner(owner);

        Comment comment = new Comment();
        comment.setId(10L);
        comment.setText("Great item!");
        comment.setAuthor(owner);

        // Устанавливаем комментарии, чтобы прошла проверка if (item.getComments() != null)
        item.setComments(List.of(comment));

        ItemDto dto = ItemMapper.mapToDto(item);

        assertNotNull(dto.getComments());
        assertEquals(1, dto.getComments().size());

        CommentDto mappedComment = dto.getComments().iterator().next();
        assertEquals("Great item!", mappedComment.getText());
    }

    @Test
    void mapToDto_WithRequest_ShouldMapRequest() {
        User owner = new User();
        owner.setId(1L);

        ItemRequest request = new ItemRequest();
        request.setId(5L);

        Item item = new Item();
        item.setId(2L);
        item.setName("Test");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwner(owner);
        item.setRequest(request);

        ItemDto dto = ItemMapper.mapToDto(item);

        assertNotNull(dto.getRequest());
        assertEquals(5L, dto.getRequest().getId());
    }

    @Test
    void mapToDto_WithoutRequest_ShouldNotSetRequest() {
        User owner = new User();
        owner.setId(1L);

        Item item = new Item();
        item.setId(2L);
        item.setName("Test");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwner(owner);
        item.setRequest(null);

        ItemDto dto = ItemMapper.mapToDto(item);

        assertNull(dto.getRequest());
    }
}