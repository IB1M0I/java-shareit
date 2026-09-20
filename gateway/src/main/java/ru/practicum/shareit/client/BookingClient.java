package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;


import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit.server.url}") String serverUrl,
                         RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .build());
    }

    public ResponseEntity<Object> getBookings(Long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("state", state, "from", from, "size", size);
        return get("?state={state}&from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getBooking(Long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> createBooking(Long userId, Object body) {
        return post("", userId, body);
    }

    public ResponseEntity<Object> updateBooking(Long userId, Long bookingId, Boolean approved) {
        return patch("/" + bookingId + "?approved={approved}", userId, Map.of("approved", approved), null);
    }

    public ResponseEntity<Object> getOwnerBookings(Long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("state", state, "from", from, "size", size);
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters);
    }
}