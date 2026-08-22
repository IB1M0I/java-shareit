package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

// Маппер для преобразования сущности User в DTO
public class UserMapper {
    // Преобразует сущность User в DTO
    public static UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
