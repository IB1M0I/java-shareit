package ru.practicum.shareit.user;


import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

import java.util.HashMap;
import java.util.Map;

// Реализация сервиса для работы с пользователями
@Service
public class UserServiceImpl implements UserService {
    // Хранилище пользователей
    Map<Long, User> users = new HashMap<Long, User>();

    // Добавляет нового пользователя
    @Override
    public User addUser(User user) {
        boolean emailExists = users.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        if (emailExists) {
            throw new DuplicateEmailException("Email уже зарегистрирован");
        }

        user.setId(generateId());
        users.put(user.getId(), user);
        return user;
    }

    // Обновляет информацию о пользователе
    @Override
    public User updateUser(UpdateUserRequest updateUser, Long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        boolean emailExists = users.values().stream()
                .filter(u -> !u.getId().equals(id))
                .anyMatch(u -> u.getEmail().equals(updateUser.getEmail()));
        if (emailExists) {
            throw new DuplicateEmailException("Email уже зарегистрирован");
        }
        User user = users.get(id);
        if (updateUser.hasEmail()) {
            user.setEmail(updateUser.getEmail());
        }
        if (updateUser.hasName()) {
            user.setName(updateUser.getName());
        }
        return user;

    }

    // Получает пользователя по идентификатору
    @Override
    public User getUser(Long id) {
        return users.get(id);
    }

    // Удаляет пользователя
    @Override
    public void deleteUser(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        users.remove(id);
    }

    // Генерирует уникальный идентификатор для пользователя
    private long generateId() {
        return users.keySet().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0L) + 1;
    }
}
