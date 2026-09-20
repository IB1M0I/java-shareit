package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class BookingDtoTest {
    @Autowired
    private JacksonTester<BookingDto> json;

    @Test
    void serializeBookingDto() throws Exception {
        BookingDto booking = new BookingDto();
        booking.setId(1L);
        booking.setStart(LocalDateTime.of(2026, 9, 21, 10, 0));
        booking.setEnd(LocalDateTime.of(2026, 9, 22, 10, 0));
        booking.setStatus("WAITING");

        assertThat(json.write(booking)).isEqualToJson("{\"id\":1,\"start\":\"2026-09-21T10:00:00\",\"end\":\"2026-09-22T10:00:00\",\"status\":\"WAITING\"}");
    }

    @Test
    void deserializeBookingDto() throws Exception {
        String jsonContent = "{\"id\":1,\"start\":\"2026-09-21T10:00:00\",\"end\":\"2026-09-22T10:00:00\",\"status\":\"WAITING\"}";

        assertThat(json.parse(jsonContent)).extracting(BookingDto::getStatus).isEqualTo("WAITING");
    }
}