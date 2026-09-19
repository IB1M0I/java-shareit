package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    @Column(name = "name")
    @EqualsAndHashCode.Include
    private String name;
    // Описание вещи
    @NotNull(message = "Описание не может быть пустым")
    @NotBlank(message = "Описание не может быть пустым")
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
