package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import java.util.Collection;

// Сущность вещи для аренды
@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    // Уникальный идентификатор вещи в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Название вещи
    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;
    // Описание вещи
    @Column(name = "description")
    private String description;
    // Доступность вещи для аренды (true - доступна, false - недоступна)
    @Column(name = "available")
    private Boolean available;
    // Владелец вещи (ссылка на пользователя)
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @EqualsAndHashCode.Include
    private User owner;
    // Запрос на бронирование вещи
    @ManyToOne
    @JoinColumn(name = "request_id")
    private ItemRequest request;

    // Комментарии к вещи (обратная связь)
    @OneToMany(mappedBy = "item")
    private Collection<Comment> comments;
}
