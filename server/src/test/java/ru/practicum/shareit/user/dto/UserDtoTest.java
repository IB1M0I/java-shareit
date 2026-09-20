package ru.practicum.shareit.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void updateUserRequest_ShouldCreateAndGetFields() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("New Name");
        request.setEmail("new@test.com");

        assertEquals("New Name", request.getName());
        assertEquals("new@test.com", request.getEmail());
    }

    @Test
    void userDto_ShouldCreateAndGetFields() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setName("Test");
        dto.setEmail("test@test.com");

        assertEquals(1L, dto.getId());
        assertEquals("Test", dto.getName());
        assertEquals("test@test.com", dto.getEmail());
    }

    @Test
    void hasName_WithName_ShouldReturnTrue() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("John");

        assertTrue(request.hasName());
    }

    @Test
    void hasName_WithBlankName_ShouldReturnFalse() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("   ");

        assertFalse(request.hasName());
    }

    @Test
    void hasName_WithNullName_ShouldReturnFalse() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName(null);

        assertFalse(request.hasName());
    }

    @Test
    void hasEmail_WithEmail_ShouldReturnTrue() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("test@test.com");

        assertTrue(request.hasEmail());
    }

    @Test
    void hasEmail_WithBlankEmail_ShouldReturnFalse() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("   ");

        assertFalse(request.hasEmail());
    }

    @Test
    void hasEmail_WithNullEmail_ShouldReturnFalse() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail(null);

        assertFalse(request.hasEmail());
    }
}