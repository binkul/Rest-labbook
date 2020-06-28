package com.lab.labbook.mapper;

import com.lab.labbook.entity.User;
import com.lab.labbook.entity.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User.UserBuilder()
                .id(userDto.getId())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .login(userDto.getLogin())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .blocked(userDto.isBlocked())
                .observer(userDto.isObserver())
                .role(userDto.getRole())
                .build();
    }

    public UserDto mapToDto(User user, boolean hidePassword) {
        String password = hidePassword ? "****" : user.getPassword();
        return new UserDto(user.getId(),
                user.getName(),
                user.getLastName(),
                user.getLogin(),
                user.getEmail(),
                password,
                user.isBlocked(),
                user.isObserver(),
                user.getRole(),
                user.getDate());
   }

    public List<UserDto> mapToListDto(List<User> users, boolean hidePassword) {
        return users.stream()
                .map(i -> mapToDto(i, hidePassword))
                .collect(Collectors.toList());
    }
}
