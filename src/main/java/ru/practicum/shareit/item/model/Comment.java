package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    // Уникальный идентификатор комментария в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Текст комментария
    @Column(name = "text")
    private String text;
    // Автор комментария (ссылка на пользователя)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    // Вещь, к которой оставлен комментарий
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    // Дата и время создания комментария
    @Column(name = "created")
    private LocalDateTime created;
}
