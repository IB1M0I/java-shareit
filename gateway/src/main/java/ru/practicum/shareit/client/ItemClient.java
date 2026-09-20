package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit.server.url}") String serverUrl,
                      RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build());
    }

    public ResponseEntity<Object> getItems(Long userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("from", from, "size", size);
        return get("?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getItemById(Long itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<Object> createItem(Long userId, Object body) {
        return post("", userId, body);
    }

    public ResponseEntity<Object> updateItem(Long userId, Long itemId, Object body) {
        return patch("/" + itemId, userId, body);
    }

    public ResponseEntity<Object> searchItems(String text) {
        return get("/search?text={text}", null, Map.of("text", text));
    }

    public ResponseEntity<Object> addComment(Long userId, Long itemId, Object body) {
        return post("/" + itemId + "/comment", userId, body);
    }
}