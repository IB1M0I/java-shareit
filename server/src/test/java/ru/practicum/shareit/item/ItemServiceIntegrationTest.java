package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Test
    void addItem_ShouldSaveItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = new NewItemDto();
        dto.setName("Test Item");
        dto.setDescription("Test Desc");
        dto.setAvailable(true);

        Item saved = itemService.addItem(dto, user.getId());

        assertNotNull(saved.getId());
        assertEquals("Test Item", saved.getName());
    }

    @Test
    void updateItem_ShouldUpdateItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = new NewItemDto();
        dto.setName("Orig");
        dto.setDescription("Orig");
        dto.setAvailable(true);
        Item saved = itemService.addItem(dto, user.getId());

        UpdateItemRequest updateRequest = new UpdateItemRequest();
        updateRequest.setName("Updated");

        Item updated = itemService.updateItem(updateRequest, saved.getId(), user.getId());

        assertEquals("Updated", updated.getName());
    }

    @Test
    void getItem_ShouldReturnItem() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = new NewItemDto();
        dto.setName("Find Me");
        dto.setDescription("Desc");
        dto.setAvailable(true);
        Item saved = itemService.addItem(dto, user.getId());

        Item found = itemService.getItem(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void searchItem_ShouldReturnMatchingItems() {
        User user = createUser("Owner", "owner@example.com");
        NewItemDto dto = new NewItemDto();
        dto.setName("Laptop");
        dto.setDescription("Computer");
        dto.setAvailable(true);
        itemService.addItem(dto, user.getId());

        var results = itemService.searchItem("LAPTOP");

        assertFalse(results.isEmpty());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.addUser(user);
    }
}