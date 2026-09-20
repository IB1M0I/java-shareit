package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.dto.item.CommentDto;
import ru.practicum.shareit.dto.item.ItemDto;
import ru.practicum.shareit.dto.item.NewItemDto;

import java.util.Map;

// Клиент для работы с вещами через API
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
        return get("?from={from}&size={size}", userId, parameters, Object.class);
    }

    public ResponseEntity<ItemDto> getItemById(Long itemId) {
        return get("/" + itemId, null, null, ItemDto.class);
    }

    public ResponseEntity<ItemDto> createItem(Long userId, NewItemDto body) {
        return post("", userId, null, body, ItemDto.class);
    }

    public ResponseEntity<ItemDto> updateItem(Long userId, Long itemId, NewItemDto body) {
        return patch("/" + itemId, userId, null, body, ItemDto.class);
    }

    public ResponseEntity<Object> searchItems(String text) {
        return get("/search?text={text}", null, Map.of("text", text), Object.class);
    }

    public ResponseEntity<CommentDto> addComment(Long userId, Long itemId, Object body) {
        return post("/" + itemId + "/comment", userId, null, body, CommentDto.class);
    }
}