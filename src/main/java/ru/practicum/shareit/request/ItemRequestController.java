package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;

import java.util.List;

// REST контроллер для управления запросами на вещи
/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestBody @Valid NewItemRequestDto newItemRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long userId ){
        return ItemRequestMapper.mapToDto(itemRequestService.addItemRequest(newItemRequest,userId));
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getItemRequestItem(userId).stream()
                .map(ItemRequestMapper::mapToDto)
                .toList();
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getItemRequestAll(){
        return itemRequestService.getItemRequestItemAll().stream()
                .map(ItemRequestMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(
            @PathVariable Long requestId) {
        return itemRequestService.getRequestById(requestId);
    }



}
