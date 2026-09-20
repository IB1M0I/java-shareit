package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.NewBookingDto;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.NewCommentDto;
import ru.practicum.shareit.item.dto.NewItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Test
    void addComment_ShouldSaveComment() {
        User owner = createUser("Owner", "owner@example.com");
        User booker = createUser("Booker", "booker@example.com");

        NewItemDto itemDto = new NewItemDto();
        itemDto.setName("Item");
        itemDto.setDescription("Desc");
        itemDto.setAvailable(true);
        var item = itemService.addItem(itemDto, owner.getId());

        NewBookingDto bookingDto = new NewBookingDto();
        bookingDto.setItemId(item.getId());
        bookingDto.setStart(LocalDateTime.now().minusDays(2));
        bookingDto.setEnd(LocalDateTime.now().minusDays(1));

        var booking = bookingService.addBooking(bookingDto, booker.getId());
        bookingService.approvedBooking(owner.getId(), booking.getId(), true);

        NewCommentDto commentDto = new NewCommentDto();
        commentDto.setText("Great item!");

        CommentDto saved = itemService.addComment(item.getId(), booker.getId(), commentDto);

        assertNotNull(saved.getId());
        assertEquals("Great item!", saved.getText());
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        return userService.addUser(user);
    }
}