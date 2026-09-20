package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemService itemService;

    @Test
    void addItem_ShouldReturnCreatedItem() throws Exception {
        NewItemDto dto = new NewItemDto();
        dto.setName("Test");
        dto.setDescription("Desc");
        dto.setAvailable(true);

        Item saved = new Item();
        saved.setId(1L);
        saved.setName("Test");

        when(itemService.addItem(any(NewItemDto.class), eq(1L))).thenReturn(saved);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getItem_ShouldReturnItem() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setName("Test");

        when(itemService.getItem(1L)).thenReturn(item);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getItems_ShouldReturnUserItems() throws Exception {
        Item item = new Item();
        item.setId(1L);

        when(itemService.getItems(1L)).thenReturn(List.of(item));

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void updateItems_ShouldReturnUpdatedItem() throws Exception {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setName("Updated");

        Item updated = new Item();
        updated.setId(1L);
        updated.setName("Updated");

        when(itemService.updateItem(any(UpdateItemRequest.class), eq(1L), eq(1L))).thenReturn(updated);

        mockMvc.perform(patch("/items/1")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void searchItem_ShouldReturnMatchingItems() throws Exception {
        Item item = new Item();
        item.setId(1L);

        when(itemService.searchItem("test")).thenReturn(List.of(item));

        mockMvc.perform(get("/items/search")
                        .param("text", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void addComment_ShouldReturnCreatedComment() throws Exception {
        NewCommentDto commentDto = new NewCommentDto();
        commentDto.setText("Great!");

        CommentDto saved = new CommentDto();
        saved.setId(1L);
        saved.setText("Great!");

        when(itemService.addComment(eq(1L), eq(1L), any(NewCommentDto.class))).thenReturn(saved);

        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Great!"));
    }


}