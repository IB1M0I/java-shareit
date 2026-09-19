package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class NewItemRequestDto {
    @NotBlank(message = "Описание не может быть пустым")
    @NotNull(message = "Описание не может быть пустым")
    private String description;
}
