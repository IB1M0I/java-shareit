package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UpdateUserRequest;

// Интерфейс сервиса для работы с пользователями
public interface UserService {
    // Добавляет нового пользователя
    User addUser(User user);

    // Обновляет информацию о пользователе
    User updateUser(UpdateUserRequest user, Long id);

    // Получает пользователя по идентификатору
    User getUser(Long id);

    // Удаляет пользователя
    void deleteUser(Long id);
}
