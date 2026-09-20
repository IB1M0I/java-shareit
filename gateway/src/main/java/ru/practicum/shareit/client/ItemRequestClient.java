package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.dto.request.ItemRequestDto;
import ru.practicum.shareit.dto.request.NewItemRequestDto;

// Клиент для работы с запросами на вещи через API
@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public ItemRequestClient(@Value("${shareit.server.url}") String serverUrl,
                             RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build());
    }

    public ResponseEntity<Object> getUserRequests(Long userId) {
        return get("", userId, null, Object.class);
    }

    public ResponseEntity<Object> getAllRequests() {
        return get("/all", null, null, Object.class);
    }

    public ResponseEntity<ItemRequestDto> getRequestById(Long userId, Long requestId) {
        return get("/" + requestId, userId, null, ItemRequestDto.class);
    }

    public ResponseEntity<ItemRequestDto> createRequest(Long userId, NewItemRequestDto body) {
        return post("", userId, null, body, ItemRequestDto.class);
    }
}