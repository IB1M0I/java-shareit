package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.dto.user.NewUserDto;
import ru.practicum.shareit.dto.user.UpdateUserDto;
import ru.practicum.shareit.dto.user.UserDto;

// Клиент для работы с пользователями через API
@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit.server.url}") String serverUrl,
                      RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build());
    }

    public ResponseEntity<Object> getUsers() {
        return get("", null, null, Object.class);
    }

    public ResponseEntity<UserDto> getUser(Long userId) {
        return get("/" + userId, null, null, UserDto.class);
    }

    public ResponseEntity<UserDto> createUser(NewUserDto body) {
        return post("", null, null, body, UserDto.class);
    }

    public ResponseEntity<UserDto> updateUser(Long userId, UpdateUserDto body) {
        return patch("/" + userId, null, null, body, UserDto.class);
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        return delete("/" + userId, null, null, Object.class);
    }
}