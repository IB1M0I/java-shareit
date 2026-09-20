package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemRequestMapperTest {

    @Test
    void mapToDto_WithRequester_ShouldSetUserId() {
        User requester = new User();
        requester.setId(1L);

        ItemRequest request = new ItemRequest();
        request.setId(5L);
        request.setDescription("Need laptop");
        request.setCreated(LocalDateTime.now());
        request.setRequester(requester);

        ItemRequestDto dto = ItemRequestMapper.mapToDto(request);

        assertEquals(5L, dto.getId());
        assertEquals("Need laptop", dto.getDescription());
        assertEquals(1L, dto.getUserId());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }

    @Test
    void mapToDto_WithoutRequester_ShouldNotSetUserId() {
        ItemRequest request = new ItemRequest();
        request.setId(5L);
        request.setDescription("Need laptop");
        request.setCreated(LocalDateTime.now());
        request.setRequester(null);

        ItemRequestDto dto = ItemRequestMapper.mapToDto(request);

        assertEquals(5L, dto.getId());
        assertEquals("Need laptop", dto.getDescription());
        assertNull(dto.getUserId());
        assertNotNull(dto.getItems());
        assertTrue(dto.getItems().isEmpty());
    }
}
