package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingMapperDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

// Реализация сервиса для работы с вещами
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    // Репозиторий для работы с вещами
    private final ItemRepository itemRepository;
    // Репозиторий для работы с пользователями
    private final UserRepository userRepository;
    // Репозиторий для работы с бронированиями
    private final BookingRepository bookingRepository;
    // Репозиторий для работы с комментариями
    private final CommentRepository commentRepository;

    // Добавляет новую вещь в базу данных
    @Override
    public Item addItem(Item item, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (item.getAvailable() == null) {
            throw new ValidationException("Поле 'available' не может быть null");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Поле 'name' не может быть null или пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Поле 'description' не может быть null или пустым");
        }

        item.setOwner(user);
        return itemRepository.save(item);
    }

    // Обновляет информацию о вещи
    @Override
    public Item updateItem(UpdateItemRequest updateItem, Long id, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));

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

        return itemRepository.save(item);
    }

    // Получает все вещи пользователя
    @Override
    public Collection<Item> getItems(Long userId) {
        return itemRepository.findByOwnerId(userId);
    }

    // Получает вещь по идентификатору
    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
    }

    // Удаляет вещь из базы данных
    @Override
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NotFoundException("Предмет не найден");
        }
        itemRepository.deleteById(id);
    }

    // Ищет вещи по тексту в названии или описании
    public Collection<Item> searchItem(String text) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return itemRepository.searchItems(text);
    }

    // Получает вещи владельца с информацией о бронированиях
    public List<ItemDto> getOwnerItems(Long userId) {
        Collection<Item> items = itemRepository.findByOwnerId(userId);

        return items.stream()
                .map(item -> {
                    ItemDto dto = ItemMapper.mapToDto(item);

                    // Находим все бронирования этой вещи
                    List<Booking> bookings = bookingRepository.findByItemId(item.getId());

                    // Последнее бронирование (start <= сейчас)
                    Booking last = bookings.stream()
                            .filter(b -> b.getStart().isBefore(LocalDateTime.now()) ||
                                    b.getStart().isEqual(LocalDateTime.now()))
                            .sorted(Comparator.comparing(Booking::getStart).reversed())
                            .findFirst()
                            .orElse(null);

                    // Ближайшее будущее (start > сейчас)
                    Booking next = bookings.stream()
                            .filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                            .sorted(Comparator.comparing(Booking::getStart))
                            .findFirst()
                            .orElse(null);

                    dto.setLastBooking(last != null ? BookingMapperDto.toBookingShortDto(last) : null);
                    dto.setNextBooking(next != null ? BookingMapperDto.toBookingShortDto(next) : null);

                    return dto;
                })
                .toList();
    }

    // Добавляет комментарий к вещи (проверяет наличие завершенного бронирования)
    public CommentDto addComment(Long itemId, Long userId, NewCommentDto newComment) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));

        boolean hasCompletedBooking = bookingRepository.existsByItemIdAndBookerIdAndEndBeforeAndStatusApproved(
                itemId, userId, LocalDateTime.now()
        );

        if (!hasCompletedBooking) {
            throw new ValidationException(
                    "Нельзя оставить комментарий к вещи, которую вы не бронировали или бронирование ещё не завершено и подтверждено"
            );
        }

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        comment.setText(newComment.getText());

        commentRepository.save(comment);

        return CommentMapper.mapToDto(comment);
    }

}