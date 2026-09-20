package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingShortDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Реализация сервиса для работы с вещами
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    // Репозиторий для работы с вещами
    private final ItemRepository itemRepository;
    // Репозиторий для работы с пользователями
    private final UserRepository userRepository;
    // Репозиторий для работы с бронированиями
    private final BookingRepository bookingRepository;
    // Репозиторий для работы с комментариями
    private final CommentRepository commentRepository;
    // Репозиторий для работы с запросами на вещи
    private final ItemRequestRepository itemRequestRepository;

    // Добавляет новую вещь в базу данных
    @Transactional
    @Override
    public Item addItem(NewItemDto newItemDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (newItemDto.getAvailable() == null) {
            throw new ValidationException("Поле 'available' не может быть null");
        }
        if (newItemDto.getName() == null || newItemDto.getName().isBlank()) {
            throw new ValidationException("Поле 'name' не может быть null или пустым");
        }
        if (newItemDto.getDescription() == null || newItemDto.getDescription().isBlank()) {
            throw new ValidationException("Поле 'description' не может быть null или пустым");
        }

        Item item = new Item();
        item.setName(newItemDto.getName());
        item.setDescription(newItemDto.getDescription());
        item.setAvailable(newItemDto.getAvailable());
        item.setOwner(user);

        if (newItemDto.getRequestId() != null) {
            ItemRequest request = itemRequestRepository.findById(newItemDto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Запрос не найден"));
            item.setRequest(request);
        }

        Item saved = itemRepository.save(item);
        return saved;
    }

    // Обновляет информацию о вещи
    @Override
    @Transactional
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
    @Transactional
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
        List<Item> items = itemRepository.findByOwnerId(userId);

        if (items.isEmpty()) {
            return List.of();
        }

        List<Long> itemIds = items.stream()
                .map(Item::getId)
                .toList();

        List<Booking> allBookings = bookingRepository.findByItemIdIn(itemIds);

        Map<Long, List<Booking>> bookingsByItem = allBookings.stream()
                .collect(Collectors.groupingBy(b -> b.getItem().getId()));

        return items.stream()
                .map(item -> {
                    ItemDto dto = ItemMapper.mapToDto(item);

                    List<Booking> itemBookings = bookingsByItem.getOrDefault(item.getId(), List.of());

                    BookingShortDto lastBooking = itemBookings.stream()
                            .filter(b -> !b.getStart().isAfter(LocalDateTime.now()))
                            .sorted(Comparator.comparing(Booking::getStart).reversed())
                            .findFirst()
                            .map(BookingMapper::mapToBookingShort)
                            .orElse(null);

                    BookingShortDto nextBooking = itemBookings.stream()
                            .filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                            .sorted(Comparator.comparing(Booking::getStart))
                            .findFirst()
                            .map(BookingMapper::mapToBookingShort)
                            .orElse(null);

                    dto.setLastBooking(lastBooking);
                    dto.setNextBooking(nextBooking);

                    return dto;
                })
                .toList();
    }

    // Добавляет комментарий к вещи (проверяет наличие завершенного бронирования)
    @Transactional
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