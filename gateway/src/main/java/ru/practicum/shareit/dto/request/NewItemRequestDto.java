package ru.practicum.shareit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewItemRequestDto {
    @NotBlank(message = "Описание не может быть пустым")
    @NotNull(message = "Описание не может быть пустым")
    private String description;
}
