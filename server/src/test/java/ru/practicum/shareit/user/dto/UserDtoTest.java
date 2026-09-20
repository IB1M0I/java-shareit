package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class UserDtoTest {

    @Autowired
    private JacksonTester<UserDto> json;

    @Test
    void serializeUserDto() throws Exception {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@example.com");

        assertThat(json.write(user)).isEqualToJson("{\"id\":1,\"name\":\"Test\",\"email\":\"test@example.com\"}");
    }
}