package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

// Интерфейс сервиса для работы с вещами
public interface ItemService {
    // Добавляет новую вещь в базу данных
    Item addItem(NewItemDto newItemDto, Long userId);

    // Обновляет информацию о вещи
    Item updateItem(UpdateItemRequest item, Long id, Long userId);

    // Получает все вещи пользователя
    Collection<Item> getItems(Long userId);

    // Получает вещь по идентификатору
    Item getItem(Long id);

    // Ищет вещи по тексту в названии или описании
    Collection<Item> searchItem(String text);

    // Удаляет вещь из базы данных
    void deleteItem(Long id);

    // Добавляет комментарий к вещи
    CommentDto addComment(Long itemId, Long userId, NewCommentDto newComment);
}
