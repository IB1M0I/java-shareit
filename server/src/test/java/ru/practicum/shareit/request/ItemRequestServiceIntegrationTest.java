package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
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

    @Test
    void getRequestById_NotFound_ShouldThrowException() {
        assertThrows(NotFoundException.class, () -> {
            itemRequestService.getRequestById(9999L);
        });
    }

    @Test
    void getItemRequestItem_ShouldReturnUserRequests() {
        User user = createUser("User", "user@test.com");

        NewItemRequestDto requestDto1 = new NewItemRequestDto();
        requestDto1.setDescription("Request1");
        itemRequestService.addItemRequest(requestDto1, user.getId());

        NewItemRequestDto requestDto2 = new NewItemRequestDto();
        requestDto2.setDescription("Request2");
        itemRequestService.addItemRequest(requestDto2, user.getId());

        var requests = itemRequestService.getItemRequestItem(user.getId());

        assertEquals(2, requests.size());
    }

    @Test
    void getItemRequestItem_EmptyList_ShouldReturnEmpty() {
        User user = createUser("User", "user@test.com");
        var requests = itemRequestService.getItemRequestItem(user.getId());
        assertTrue(requests.isEmpty());
    }



}