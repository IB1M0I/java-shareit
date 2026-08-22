package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

// Маппер для преобразования сущности ItemRequest в DTO
public class ItemRequestMapper {
    // Преобразует сущность ItemRequest в ItemRequestDto
    public static ItemRequestDto mapToDto(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setRequester(itemRequest.getRequester());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setCreated(itemRequest.getCreated());

        return itemRequestDto;
    }
}
