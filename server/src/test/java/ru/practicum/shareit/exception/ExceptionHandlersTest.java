package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlersTest {

    private final ExceptionHandlers exceptionHandlers = new ExceptionHandlers();

    @Test
    void handleDuplicateEmailException_ShouldReturnConflictMap() {
        DuplicateEmailException ex = new DuplicateEmailException("Email exists");
        Map<String, String> result = exceptionHandlers.handleDuplicateEmailException(ex);

        assertEquals("Email exists", result.get("error"));
    }

    @Test
    void handleNotFoundException_ShouldReturnNotFoundMap() {
        NotFoundException ex = new NotFoundException("Not found");
        Map<String, String> result = exceptionHandlers.handleNotFoundException(ex);

        assertEquals("Not found", result.get("error"));
    }

    @Test
    void handleValidationException_ShouldReturnBadRequestMap() {
        ValidationException ex = new ValidationException("Invalid data");
        Map<String, String> result = exceptionHandlers.handleValidationException(ex);

        assertEquals("Invalid data", result.get("error"));
    }

    @Test
    void handleForbiddenException_ShouldReturnForbiddenMap() {
        ForbiddenException ex = new ForbiddenException("Access denied");
        Map<String, String> result = exceptionHandlers.handleForbiddenException(ex);

        assertEquals("Access denied", result.get("error"));
    }
}