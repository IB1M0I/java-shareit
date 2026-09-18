package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность запроса на бронирование вещи
@Data
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
    private LocalDateTime created;
}
