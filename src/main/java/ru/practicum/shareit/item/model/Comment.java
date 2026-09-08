package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность комментария к вещи
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @EqualsAndHashCode.Include
    private User author;
    // Вещь, к которой оставлен комментарий
    @ManyToOne
    @JoinColumn(name = "item_id")
    @EqualsAndHashCode.Include
    private Item item;
    // Дата и время создания комментария
    @Column(name = "created")
    @EqualsAndHashCode.Include
    private LocalDateTime created;
}
