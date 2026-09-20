package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemShortDto;
import ru.practicum.shareit.request.dto.NewItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

// Сервис для работы с запросами на вещи
@RequiredArgsConstructor
@Service
public class ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    // Создает новый запрос на вещь
    public ItemRequest addItemRequest(NewItemRequestDto newItemRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(newItemRequest.getDescription());
        itemRequest.setRequester(user);
        itemRequest.setCreated(LocalDateTime.now());
        return itemRequestRepository.save(itemRequest);
    }

    // Получает все запросы пользователя
    public List<ItemRequest> getItemRequestItem(Long userId) {
        return itemRequestRepository.findAllByRequesterIdOrderByCreatedDesc(userId);
    }

    // Получает все запросы на вещи
    public List<ItemRequest> getItemRequestItemAll() {
        return itemRequestRepository.findAll();
    }

    // Получает запрос по ID с вещами
    public ItemRequestDto getRequestById(Long requestId) {
        ItemRequest request = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));

        List<Item> items = itemRepository.findByRequestId(requestId);

        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreated(request.getCreated());

        if (request.getRequester() != null) {
            dto.setUserId(request.getRequester().getId());
        }

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
