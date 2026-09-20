package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

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
        return get("");
    }

    public ResponseEntity<Object> getUser(Long userId) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> createUser(Object body) {
        return post("", body);
    }

    public ResponseEntity<Object> updateUser(Long userId, Object body) {
        return patch("/" + userId, body);
    }

    public ResponseEntity<Object> deleteUser(Long userId) {
        return delete("/" + userId);
    }
}