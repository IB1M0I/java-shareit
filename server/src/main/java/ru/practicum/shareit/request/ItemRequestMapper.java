package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.ArrayList;

// Маппер для преобразования сущности ItemRequest в DTO
public class ItemRequestMapper {
    // Преобразует сущность ItemRequest в ItemRequestDto
    public static ItemRequestDto mapToDto(ItemRequest request) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreated(request.getCreated());

        if (request.getRequester() != null) {
            dto.setUserId(request.getRequester().getId());
        }

        dto.setItems(new ArrayList<>());
        return dto;
    }

}
