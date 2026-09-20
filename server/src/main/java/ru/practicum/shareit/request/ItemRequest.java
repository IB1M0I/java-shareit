package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность запроса на бронирование вещи
@Setter
@Getter
@Entity
@Table(name = "requests")
public class ItemRequest {
    // Уникальный идентификатор запроса в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Описание запрашиваемой вещи
    @Column(name = "description")
    private String description;
    // Пользователь, создавший запрос
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    // Дата и время создания запроса
    @Column(name = "created")
    private LocalDateTime created;
}
