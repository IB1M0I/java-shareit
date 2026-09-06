package ru.practicum.shareit.booking;

import jakarta.persistence.*;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

// Сущность бронирования вещи
@Data
@Entity
@Table(name = "bookings")
public class Booking {
    // Уникальный идентификатор бронирования в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Дата и время начала аренды
    @Column(name = "start_date")
    private LocalDateTime start;
    // Дата и время окончания аренды
    @Column(name = "end_date")
    private LocalDateTime end;
    // Арендуемая вещь
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    // Пользователь, бронирующий вещь
    @ManyToOne
    @JoinColumn(name = "booker_id")
    private User booker;
    // Статус бронирования (WAITING, APPROVED, REJECTED, CANCELED)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}