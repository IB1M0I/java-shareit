package ru.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.UserClient;
import ru.practicum.shareit.dto.user.NewUserDto;
import ru.practicum.shareit.dto.user.UpdateUserDto;
import ru.practicum.shareit.dto.user.UserDto;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody NewUserDto dto) {
        log.info("Creating user: {}", dto);
        return userClient.createUser(dto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @Valid @RequestBody UpdateUserDto dto) {
        log.info("Updating user {} with {}", userId, dto);
        return userClient.updateUser(userId, dto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        log.info("Getting user {}", userId);
        return userClient.getUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        log.info("Getting all users");
        return userClient.getUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        log.info("Deleting user {}", userId);
        return userClient.deleteUser(userId);
    }
}