package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторий для работы с пользователями
public interface UserRepository extends JpaRepository<User,Long> {
    // Проверяет существование пользователя по email
    boolean existsByEmail(String email);

    // Проверяет существование пользователя по email, исключая указанный ID
    boolean existsByEmailAndIdNot(String email, Long id);
}
