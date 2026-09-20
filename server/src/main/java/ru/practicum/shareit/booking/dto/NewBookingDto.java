package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

// DTO для создания нового бронирования
@Data
public class NewBookingDto {
    // Идентификатор бронируемой вещи
    private Long itemId;
    // Дата и время начала аренды
    private LocalDateTime start;
    // Дата и время окончания аренды
    private LocalDateTime end;
}
