package ru.practicum.shareit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.BookingClient;
import ru.practicum.shareit.dto.booking.NewBookingDto;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {

	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
												@Valid @RequestBody NewBookingDto dto) {
		log.info("Creating booking: {} by user {}", dto, userId);
		return bookingClient.createBooking(userId, dto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> updateBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
												@PathVariable Long bookingId,
												@RequestParam Boolean approved) {
		log.info("Updating booking {} by user {}", bookingId, userId);
		return bookingClient.updateBooking(userId, bookingId, approved);
	}

	// ✅ 1. Сначала конкретный путь /owner
	@GetMapping("/owner")
	public ResponseEntity<Object> getOwnerBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
												   @RequestParam(defaultValue = "ALL") String state,
												   @RequestParam(defaultValue = "0") Integer from,
												   @RequestParam(defaultValue = "10") Integer size) {
		log.info("Getting owner bookings by user {} with state {}", userId, state);
		return bookingClient.getOwnerBookings(userId, state, from, size);
	}

	// ✅ 2. Потом шаблонный путь /{bookingId}
	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
											 @PathVariable Long bookingId) {
		log.info("Getting booking {} by user {}", bookingId, userId);
		return bookingClient.getBooking(userId, bookingId);
	}

	@GetMapping
	public ResponseEntity<Object> getBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
											  @RequestParam(defaultValue = "ALL") String state,
											  @RequestParam(defaultValue = "0") Integer from,
											  @RequestParam(defaultValue = "10") Integer size) {
		log.info("Getting bookings by user {} with state {}", userId, state);
		return bookingClient.getBookings(userId, state, from, size);
	}
}