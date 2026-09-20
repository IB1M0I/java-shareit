package ru.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.ItemRequestClient;
import ru.practicum.shareit.dto.request.NewItemRequestDto;


@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {

    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @Valid @RequestBody NewItemRequestDto dto) {
        log.info("Creating request: {} by user {}", dto, userId);
        return itemRequestClient.createRequest(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting user requests by user {}", userId);
        return itemRequestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequests() {
        log.info("Getting all requests");
        return itemRequestClient.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long requestId) {
        log.info("Getting request {} by user {}", requestId, userId);
        return itemRequestClient.getRequestById(userId, requestId);
    }
}