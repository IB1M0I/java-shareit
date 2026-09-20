package ru.practicum.shareit.booking.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewBookingDtoTest {

    @Test
    void newBookingDto_ShouldCreateAndGetFields() {
        NewBookingDto dto = new NewBookingDto();
        dto.setItemId(1L);
        dto.setStart(LocalDateTime.now());
        dto.setEnd(LocalDateTime.now().plusDays(1));

        assertEquals(1L, dto.getItemId());
        assertNotNull(dto.getStart());
        assertNotNull(dto.getEnd());
    }
}
