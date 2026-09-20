package ru.practicum.shareit.request;

import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemShortDto;

import java.util.ArrayList;
import java.util.List;

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

    // Преобразует сущность ItemRequest в ItemRequestDto с загруженными вещами
    public static ItemRequestDto mapToDtoWithItems(ItemRequest request, ItemRepository itemRepository) {
        ItemRequestDto dto = mapToDto(request);

        List<Item> items = itemRepository.findByRequestId(request.getId());
        List<ItemShortDto> itemDtos = items.stream()
                .map(item -> new ItemShortDto(
                        item.getId(),
                        item.getName(),
                        item.getOwner().getId()
                ))
                .toList();

        dto.setItems(itemDtos);
        return dto;
    }

}
