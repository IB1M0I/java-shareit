package ru.practicum.shareit.exception;

// Исключение при отсутствии прав доступа к ресурсу
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
