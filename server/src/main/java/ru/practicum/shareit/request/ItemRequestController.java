package ru.practicum.shareit.request;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

// REST контроллер для управления запросами на вещи
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;
    private final ItemRepository itemRepository;

    // Создает новый запрос на вещь
    @PostMapping
    public ItemRequestDto addItemRequest(@RequestBody NewItemRequestDto newItemRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemRequestMapper.mapToDtoWithItems(itemRequestService.addItemRequest(newItemRequest, userId), itemRepository);
    }

    // Получает все запросы пользователя
    @GetMapping
    java.util.List<ItemRequestDto> getItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getItemRequestItem(userId).stream()
                .map(request -> ItemRequestMapper.mapToDtoWithItems(request, itemRepository))
                .toList();
    }

    // Получает все запросы на вещи
    @GetMapping("/all")
    java.util.List<ItemRequestDto> getItemRequestAll() {
        return itemRequestService.getItemRequestItemAll().stream()
                .map(request -> ItemRequestMapper.mapToDtoWithItems(request, itemRepository))
                .toList();
    }

    // Получает запрос по ID
    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(
            @PathVariable Long requestId) {
        return itemRequestService.getRequestById(requestId);
    }

}
