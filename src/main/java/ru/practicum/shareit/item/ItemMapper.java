package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

// Маппер для преобразования сущности Item в DTO
public class ItemMapper {

    // Преобразует сущность Item в DTO
    public static ItemDto mapToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(item.getOwner());

        if (item.getRequest() != null) {
            itemDto.setRequest(item.getRequest());
        }

        return itemDto;
    }

}
