package ru.practicum.shareit.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.item.dto.ItemRequestDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemRequestDtoTest {

    @Autowired
    private JacksonTester<ItemRequestDto> json;

    @Test
    void serializeItemRequestDto() throws Exception {
        ItemRequestDto request = new ItemRequestDto();
        request.setId(1L);
        request.setDescription("Need laptop");
        request.setCreated(LocalDateTime.of(2026, 9, 20, 10, 0));

        assertThat(json.write(request)).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(json.write(request)).extractingJsonPathStringValue("$.description").isEqualTo("Need laptop");
    }

    @Test
    void deserializeItemRequestDto() throws Exception {
        String jsonContent = "{\"id\":1,\"description\":\"Need laptop\",\"created\":\"2026-09-20T10:00:00\"}";

        assertThat(json.parse(jsonContent)).extracting(ItemRequestDto::getDescription).isEqualTo("Need laptop");
    }
}