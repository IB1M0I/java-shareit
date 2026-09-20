package ru.practicum.shareit.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

// DTO для создания нового бронирования
@Data
public class NewBookingDto {
    // Идентификатор бронируемой вещи
    @NotNull(message = "ID вещи не может быть пустым")
    private Long itemId;
    // Дата и время начала аренды
    @NotNull(message = "Дата начала не может быть пустой")
    @FutureOrPresent(message = "Дата начала должна быть в будущем или настоящем")
    private LocalDateTime start;
    // Дата и время окончания аренды
    @NotNull(message = "Дата окончания не может быть пустой")
    @Future(message = "Дата окончания должна быть в будущем")
    private LocalDateTime end;
}
