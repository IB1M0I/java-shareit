package ru.practicum.shareit.dto.booking;

import lombok.Data;

import java.time.LocalDateTime;

// DTO для краткой информации о бронировании
@Data
public class BookingShortDto {
    // Уникальный идентификатор бронирования
    private Long id;
    // Идентификатор бронирующего пользователя
    private Long bookerId;
    // Дата и время начала аренды
    private LocalDateTime start;
    // Дата и время окончания аренды
    private LocalDateTime end;
}
