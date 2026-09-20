package ru.practicum.shareit.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.dto.booking.BookingDto;
import ru.practicum.shareit.dto.booking.NewBookingDto;

import java.util.Map;

// Клиент для работы с бронированиями через API
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
        return get("?state={state}&from={from}&size={size}", userId, parameters, Object.class);
    }

    public ResponseEntity<BookingDto> getBooking(Long userId, Long bookingId) {
        return get("/" + bookingId, userId, null, BookingDto.class);
    }

    public ResponseEntity<BookingDto> createBooking(Long userId, NewBookingDto body) {
        return post("", userId, null, body, BookingDto.class);
    }

    public ResponseEntity<BookingDto> updateBooking(Long userId, Long bookingId, Boolean approved) {
        return patch("/" + bookingId + "?approved={approved}", userId, Map.of("approved", approved), null, BookingDto.class);
    }

    public ResponseEntity<Object> getOwnerBookings(Long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of("state", state, "from", from, "size", size);
        return get("/owner?state={state}&from={from}&size={size}", userId, parameters, Object.class);
    }
}