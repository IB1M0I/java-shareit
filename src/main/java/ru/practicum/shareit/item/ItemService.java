package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

// Интерфейс сервиса для работы с вещами
public interface ItemService {
    // Добавляет новую вещь
    Item addItem(Item item, Long userId);

    // Обновляет информацию о вещи
    Item updateItem(UpdateItemRequest item, Long id, Long userId);

    // Получает все вещи пользователя
    Collection<Item> getItems(Long userId);

    // Получает вещь по идентификатору
    Item getItem(Long id);

    // Ищет вещи по тексту
    Collection<Item> searchItem(String text);

    // Удаляет вещь
    void deleteItem(Long id);
}
