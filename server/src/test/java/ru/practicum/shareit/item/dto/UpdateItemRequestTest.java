package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.*;

class UpdateItemRequestTest {

    @Test
    void hasName_WithName_ShouldReturnTrue() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setName("Test");

        assertTrue(request.hasName());
    }

    @Test
    void hasName_WithBlankName_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setName("   ");

        assertFalse(request.hasName());
    }

    @Test
    void hasName_WithNullName_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setName(null);

        assertFalse(request.hasName());
    }

    @Test
    void hasDescription_WithDescription_ShouldReturnTrue() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setDescription("Description");

        assertTrue(request.hasDescription());
    }

    @Test
    void hasDescription_WithBlankDescription_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setDescription("   ");

        assertFalse(request.hasDescription());
    }

    @Test
    void hasDescription_WithNullDescription_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setDescription(null);

        assertFalse(request.hasDescription());
    }

    @Test
    void hasAvailable_WithAvailable_ShouldReturnTrue() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setAvailable(true);

        assertTrue(request.hasAvailable());
    }

    @Test
    void hasAvailable_WithNullAvailable_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setAvailable(null);

        assertFalse(request.hasAvailable());
    }

    @Test
    void hasOwner_WithOwner_ShouldReturnTrue() {
        UpdateItemRequest request = new UpdateItemRequest();
        User owner = new User();
        request.setOwner(owner);

        assertTrue(request.hasOwner());
    }

    @Test
    void hasOwner_WithNullOwner_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setOwner(null);

        assertFalse(request.hasOwner());
    }

    @Test
    void hasRequest_WithRequest_ShouldReturnTrue() {
        UpdateItemRequest request = new UpdateItemRequest();
        ItemRequest itemRequest = new ItemRequest();
        request.setRequest(itemRequest);

        assertTrue(request.hasRequest());
    }

    @Test
    void hasRequest_WithNullRequest_ShouldReturnFalse() {
        UpdateItemRequest request = new UpdateItemRequest();
        request.setRequest(null);

        assertFalse(request.hasRequest());
    }
}
