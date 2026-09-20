package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.NewItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemRequestServiceIntegrationTest {

    @Autowired
    private ItemRequestService itemRequestService;

    @Autowired
    private UserService userService;

    @Test
    void addItemRequest_ShouldSaveRequest() {
        User user = createUser("User", "user@example.com");

        NewItemRequestDto requestDto = new NewItemRequestDto();
        requestDto.setDescription("Need laptop");

        ItemRequest saved = itemRequestService.addItemRequest(requestDto, user.getId());

        assertNotNull(saved.getId());
        assertEquals("Need laptop", saved.getDescription());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.addUser(user);
    }
}