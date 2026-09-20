package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
class ItemRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemRequestService itemRequestService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ru.practicum.shareit.user.UserRepository userRepository;

    @Test
    void addItemRequest_ShouldReturnCreatedRequest() throws Exception {
        NewItemRequestDto requestDto = new NewItemRequestDto();
        requestDto.setDescription("Need laptop");

        ItemRequest savedRequest = new ItemRequest();
        savedRequest.setId(1L);
        savedRequest.setDescription("Need laptop");

        when(itemRequestService.addItemRequest(any(NewItemRequestDto.class), eq(1L)))
                .thenReturn(savedRequest);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Need laptop"));
    }

    @Test
    void getItemRequests_ShouldReturnUserRequests() throws Exception {
        ItemRequest request = new ItemRequest();
        request.setId(1L);
        request.setDescription("Need laptop");

        when(itemRequestService.getItemRequestItem(1L)).thenReturn(List.of(request));

        mockMvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getItemRequestAll_ShouldReturnAllRequests() throws Exception {
        ItemRequest request = new ItemRequest();
        request.setId(1L);

        when(itemRequestService.getItemRequestItemAll()).thenReturn(List.of(request));

        mockMvc.perform(get("/requests/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getRequestById_ShouldReturnRequest() throws Exception {
        ItemRequestDto requestDto = new ItemRequestDto();
        requestDto.setId(1L);
        requestDto.setDescription("Need laptop");

        when(itemRequestService.getRequestById(1L)).thenReturn(requestDto);

        mockMvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Need laptop"));
    }
}