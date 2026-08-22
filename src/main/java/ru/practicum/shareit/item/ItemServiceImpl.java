package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserServiceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Реализация сервиса для работы с вещами
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    // Хранилище вещей
    private Map<Long, Item> items = new HashMap<Long, Item>();
    // Сервис для работы с пользователями
    private final UserServiceImpl userService;

    // Добавляет новую вещь
    @Override
    public Item addItem(Item item, Long userId) {
        item.setId(generateId());
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("Поле 'available' не может быть null");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Поле 'name' не может быть null или пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Поле 'name' не может быть null или пустым");
        }
        item.setOwner(user);
        items.put(item.getId(), item);
        return item;
    }

    // Обновляет информацию о вещи
    @Override
    public Item updateItem(UpdateItemRequest updateItem, Long id, Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (items.containsKey(id)) {
            Item item = items.get(id);
            if (!item.getOwner().getId().equals(user.getId())) {
                throw new ValidationException("Пользователь не является владельцем вещи");
            }
            if (updateItem.hasName()) {
                item.setName(updateItem.getName());
            }
            if (updateItem.hasDescription()) {
                item.setDescription(updateItem.getDescription());
            }
            if (updateItem.hasAvailable()) {
                item.setAvailable(updateItem.getAvailable());
            }
            if (updateItem.hasOwner()) {
                item.setOwner(updateItem.getOwner());
            }
            if (updateItem.hasRequest()) {
                item.setRequest(updateItem.getRequest());
            }

            items.put(id, item);
            return items.get(id);
        } else {
            throw new NotFoundException("Предмет не найден");
        }
    }

    // Получает все вещи пользователя
    @Override
    public Collection<Item> getItems(Long userId) {
        return items.values().stream()
                .filter(i -> i.getOwner().getId().equals(userId))
                .toList();
    }

    // Получает вещь по идентификатору
    @Override
    public Item getItem(Long id) {
        return items.get(id);
    }

    // Удаляет вещь
    @Override
    public void deleteItem(Long id) {
        items.remove(id);
    }

    // Ищет вещи по тексту в названии или описании
    public Collection<Item> searchItem(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return items.values().stream()
                .filter(i -> i.getName().toLowerCase().contains(text.toLowerCase()) ||
                        i.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(i -> i.getAvailable() != null && i.getAvailable())
                .toList();
    }

    // Генерирует уникальный идентификатор для вещи
    private long generateId() {
        return items.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
