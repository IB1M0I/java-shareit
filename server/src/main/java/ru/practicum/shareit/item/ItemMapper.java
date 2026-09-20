package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.user.UserMapper;

// Маппер для преобразования сущности Item в DTO
public class ItemMapper {

    // Преобразует сущность Item в ItemDto
    public static ItemDto mapToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setOwner(UserMapper.mapToDto(item.getOwner()));

        if (item.getRequest() != null) {
            itemDto.setRequest(ItemRequestMapper.mapToDto(item.getRequest()));
        }
        if (item.getComments() != null) {
            itemDto.setComments(CommentMapper.mapToDto(item.getComments()));
        }
        return itemDto;
    }

}
