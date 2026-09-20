package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Comment;

import java.util.Collection;

// Репозиторий для работы с комментариями
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Получает все комментарии к вещи
    Collection<Comment> findByItemId(Long itemId);
}
