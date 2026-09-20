package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ItemDtoTest {

    @Autowired
    private JacksonTester<ItemDto> json;

    @Test
    void serializeItemDto() throws Exception {
        ItemDto item = new ItemDto();
        item.setId(1L);
        item.setName("Laptop");
        item.setDescription("Computer");
        item.setAvailable(true);

        assertThat(json.write(item)).isEqualToJson("{\"id\":1,\"name\":\"Laptop\",\"description\":\"Computer\",\"available\":true}");
    }
}