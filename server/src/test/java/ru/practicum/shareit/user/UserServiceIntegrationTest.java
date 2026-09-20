package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void addUser_ShouldSaveUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        User saved = userService.addUser(user);

        assertNotNull(saved.getId());
        assertEquals("Test User", saved.getName());
    }

    @Test
    void updateUser_ShouldUpdateUser() {
        User user = new User();
        user.setName("Original");
        user.setEmail("orig@example.com");
        User saved = userService.addUser(user);

        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setName("Updated");

        User updated = userService.updateUser(updateRequest, saved.getId());

        assertEquals("Updated", updated.getName());
    }

    @Test
    void getUser_ShouldReturnUser() {
        User user = new User();
        user.setName("Find Me");
        user.setEmail("find@example.com");
        User saved = userService.addUser(user);

        User found = userService.getUser(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void deleteUser_ShouldRemoveUser() {
        User user = new User();
        user.setName("Delete Me");
        user.setEmail("del@example.com");
        User saved = userService.addUser(user);

        userService.deleteUser(saved.getId());

        assertNull(userService.getUser(saved.getId()));
    }
}