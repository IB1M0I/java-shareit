package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UpdateUserRequest;

// Реализация сервиса для работы с пользователями
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // Репозиторий для работы с пользователями
    private final UserRepository userRepository;

    // Добавляет нового пользователя в базу данных
    @Override
    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("Email уже зарегистрирован");
        }
        userRepository.save(user);
        return user;
    }

    // Обновляет информацию о пользователе
    @Override
    public User updateUser(UpdateUserRequest updateUser, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (updateUser.getEmail() != null && !updateUser.getEmail().equals(user.getEmail())) {
            boolean emailExists = userRepository.existsByEmailAndIdNot(updateUser.getEmail(), id);
            if (emailExists) {
                throw new DuplicateEmailException("Email уже зарегистрирован");
            }
            user.setEmail(updateUser.getEmail());
        }

        if (updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }
        return userRepository.save(user);
    }

    // Получает пользователя по идентификатору
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Удаляет пользователя из базы данных
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Пользователь не найден");
        }
        userRepository.deleteById(id);
    }
}

//    // Генерирует уникальный идентификатор для пользователя
//    private long generateId() {
//        return users.keySet().stream()
//                .mapToLong(Long::longValue)
//                .max()
//                .orElse(0L) + 1;
//    }

