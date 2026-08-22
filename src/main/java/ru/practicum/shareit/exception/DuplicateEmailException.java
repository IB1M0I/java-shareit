package ru.practicum.shareit.exception;

// Исключение при попытке регистрации с дублирующимся email
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
