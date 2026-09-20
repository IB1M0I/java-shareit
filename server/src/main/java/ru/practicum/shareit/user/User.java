package ru.practicum.shareit.user;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// Сущность пользователя
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {
    // Уникальный идентификатор пользователя в базе данных
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Имя пользователя
    @Column(name = "name")
    private String name;
    // Email пользователя (должен быть уникальным)
    @Column(name = "email")
    @EqualsAndHashCode.Include
    private String email;
}
