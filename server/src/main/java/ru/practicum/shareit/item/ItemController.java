package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;


// REST контроллер для управления вещами
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    // Сервис для работы с вещами
    private final ItemService itemService;

    // Добавляет новую вещь
    @PostMapping
    public ItemDto addItem(@RequestBody NewItemDto newItemDto, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemMapper.mapToDto(itemService.addItem(newItemDto, userId));
    }

    // Получает вещь по идентификатору
    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Long itemId) {
        return ItemMapper.mapToDto(itemService.getItem(itemId));
    }

    // Получает все вещи пользователя с информацией о бронированиях
    @GetMapping
    public List<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getOwnerItems(userId);
    }

    // Обновляет информацию о вещи
    @PatchMapping("{itemId}")
    public ItemDto updateItems(@RequestBody UpdateItemRequest item, @PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemMapper.mapToDto(itemService.updateItem(item, itemId, userId));
    }

    // Ищет вещи по тексту в названии или описании
    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchItem(text).stream().map(ItemMapper::mapToDto).toList();
    }

    // Добавляет комментарий к вещи
    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@PathVariable Long itemId,
                                 @RequestHeader("X-Sharer-User-Id") Long userId,
                                 @RequestBody NewCommentDto newComment) {
        return itemService.addComment(itemId, userId, newComment);
    }
}
