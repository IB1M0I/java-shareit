package ru.practicum.shareit.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Сущность пользователя
@Data
@Entity
@Table(name = "users")
public class User {
    // Уникальный идентификатор пользователя в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Имя пользователя
    @Column(name = "name")
    @NotNull(message = "Имя не может быть пустым")
    private String name;
    // Email пользователя (должен быть уникальным)
    @NotNull(message = "Email не может быть пустым")
    @Email(message = "Email не валидный")
    @Column(name = "email")
    private String email;
}
