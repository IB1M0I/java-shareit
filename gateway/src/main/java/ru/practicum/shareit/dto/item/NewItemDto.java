package ru.practicum.shareit.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewItemDto {
    @NotNull(message = "Название не может быть пустым")
    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotNull(message = "Описание не может быть пустым")
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @NotNull(message = "Доступность не может быть пустой")
    private Boolean available;

    private Long requestId;
}
