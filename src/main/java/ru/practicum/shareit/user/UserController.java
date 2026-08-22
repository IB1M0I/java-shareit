package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

// REST контроллер для управления пользователями
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // Сервис для работы с пользователями
    private final UserService userService;

    // Получает пользователя по идентификатору
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return UserMapper.mapToDto(userService.getUser(id));
    }

    // Создает нового пользователя
    @PostMapping
    public UserDto createUser(@RequestBody @Valid User user) {
        return UserMapper.mapToDto(userService.addUser(user));
    }

    // Обновляет информацию о пользователе
    @PatchMapping("/{id}")
    public UserDto updateUser(@RequestBody User user, @PathVariable Long id) {
        System.out.println("updateUser called: id=" + id);
        return UserMapper.mapToDto(userService.updateUser(user,id));
    }

    // Удаляет пользователя
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
