package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.NotFoundException;
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

    @Test
    void addUser_WithDuplicateEmail_ShouldThrowException() {
        User user1 = new User();
        user1.setName("User1");
        user1.setEmail("test@test.com");
        userService.addUser(user1);

        User user2 = new User();
        user2.setName("User2");
        user2.setEmail("test@test.com"); // Тот же email

        assertThrows(DuplicateEmailException.class, () -> {
            userService.addUser(user2);
        });
    }

    @Test
    void updateUser_NotFound_ShouldReturnNull() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("New Name");

        assertThrows(NotFoundException.class, () -> {
            userService.updateUser(request, 9999L);
        });
    }

    @Test
    void updateUser_OnlyName_ShouldKeepEmail() {
        User user = createUser("Original", "original@test.com");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("New Name");
        // Email не меняем

        User updated = userService.updateUser(request, user.getId());
        assertEquals("New Name", updated.getName());
        assertEquals("original@test.com", updated.getEmail());
    }

    // Убедись, что в этом классе есть вспомогательный метод createUser:
    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.addUser(user);
    }

    @Test
    void updateUser_NotFound_ShouldThrowException() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("New Name");

        assertThrows(NotFoundException.class, () -> {
            userService.updateUser(request, 9999L);
        });
    }

    @Test
    void updateUser_OnlyEmail_ShouldKeepName() {
        User user = createUser("Original", "original@test.com");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("newemail@test.com");

        User updated = userService.updateUser(request, user.getId());
        assertEquals("Original", updated.getName());
        assertEquals("newemail@test.com", updated.getEmail());
    }

    @Test
    void updateUser_WithDuplicateEmail_ShouldThrowException() {
        User user1 = createUser("User1", "user1@test.com");
        User user2 = createUser("User2", "user2@test.com");

        UpdateUserRequest request = new UpdateUserRequest();
        request.setEmail("user1@test.com");

        assertThrows(DuplicateEmailException.class, () -> {
            userService.updateUser(request, user2.getId());
        });
    }

    @Test
    void getUser_NotFound_ShouldReturnNull() {
        User found = userService.getUser(9999L);
        assertNull(found);
    }

}