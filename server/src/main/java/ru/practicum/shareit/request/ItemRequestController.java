package ru.practicum.shareit.request;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.util.List;

// REST контроллер для управления запросами на вещи
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    // Создает новый запрос на вещь
    @PostMapping
    public ItemRequestDto addItemRequest(@RequestBody NewItemRequestDto newItemRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemRequestMapper.mapToDto(itemRequestService.addItemRequest(newItemRequest, userId));
    }

    // Получает все запросы пользователя
    @GetMapping
    public List<ItemRequestDto> getItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getItemRequestItem(userId).stream()
                .map(ItemRequestMapper::mapToDto)
                .toList();
    }

    // Получает все запросы на вещи
    @GetMapping("/all")
    public List<ItemRequestDto> getItemRequestAll() {
        return itemRequestService.getItemRequestItemAll().stream()
                .map(ItemRequestMapper::mapToDto)
                .toList();
    }

    // Получает запрос по ID
    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(
            @PathVariable Long requestId) {
        return itemRequestService.getRequestById(requestId);
    }

}
