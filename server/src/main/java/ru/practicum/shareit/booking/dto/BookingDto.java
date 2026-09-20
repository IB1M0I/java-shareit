package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DTO для передачи данных о бронировании
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    // Уникальный идентификатор бронирования
    private Long id;
    // Дата и время начала аренды
    private LocalDateTime start;
    // Дата и время окончания аренды
    private LocalDateTime end;
    // Статус бронирования
    private String status;
    // Арендуемая вещь
    private Item item;
    // Пользователь, бронирующий вещь
    private Booker booker;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Booker {
        private Long id;
        private String name;
        private String email;
    }
}
