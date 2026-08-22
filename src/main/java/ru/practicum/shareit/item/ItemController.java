package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;


// REST контроллер для управления вещами
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    // Сервис для работы с вещами
    private final ItemService itemService;

    // Добавляет новую вещь
    @PostMapping
    public ItemDto addItem(@RequestBody @Valid Item item, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemMapper.mapToDto(itemService.addItem(item, userId));
    }

    // Получает вещь по идентификатору
    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable Long itemId) {
        return ItemMapper.mapToDto(itemService.getItem(itemId));
    }

    // Получает все вещи пользователя
    @GetMapping
    public Collection<ItemDto> getItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId).stream()
                .map(ItemMapper::mapToDto)
                .toList();
    }

    // Обновляет информацию о вещи
    @PatchMapping("{itemId}")
    public ItemDto updateItems(@RequestBody UpdateItemRequest item, @PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemMapper.mapToDto(itemService.updateItem(item, itemId, userId));
    }

    // Ищет вещи по тексту в названии или описании
    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam String text) {
        return itemService.searchItem(text).stream().map(ItemMapper::mapToDto).toList();
    }
}
