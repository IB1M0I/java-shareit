package ru.practicum.shareit.user;


import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.*;
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
    public User updateUser(User user, Long id) {
        System.out.println("updateUser: id=" + id + " users keys=" + users.keySet());
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        boolean emailExists = users.values().stream()
                .filter(u -> !u.getId().equals(id))
                .anyMatch(u -> u.getEmail().equals(user.getEmail()));
        if (emailExists) {
            throw new DuplicateEmailException("Email уже зарегистрирован");
        }
        user.setId(id);
        users.put(id, user);
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
        System.out.println("deleteUser: id=" + id);
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
