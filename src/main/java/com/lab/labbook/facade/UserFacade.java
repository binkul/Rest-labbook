package com.lab.labbook.facade;

import com.lab.labbook.entity.User;
import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.mapper.UserMapper;
import com.lab.labbook.service.UserService;
import com.lab.labbook.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService service;
    private final UserValidator validator;
    private final UserMapper mapper;

    public List<UserDto> fetchGetUsers() {
        return mapper.mapToListDto(service.getUsers(), true);
    }

    public List<UserDto> fetchGetByName(String name) {
        return mapper.mapToListDto(service.getByName(name), true);
    }

    public UserDto fetchGetById(Long id, boolean hidePassword) {
        return mapper.mapToDto(service.getById(id), hidePassword);
    }

    public UserDto fetchGetByLogin(String login) {
        return mapper.mapToDto(service.getByLogin(login), false);
    }


    public void fetchCreate(UserDto userDto) {
        User user = mapper.mapToUser(userDto);
        validator.validateRole(user.getRole());
        validator.validateLogin(user);
        validator.validateEmail(user);

        service.saveUser(user);
    }

    public UserDto fetchUpdate(UserDto userDto) {
        User user = service.getUserOrException(userDto.getId());
        validator.validateDefault(user);
        validator.validateRole(userDto.getRole());
        validator.validateLoginUpdate(userDto.getLogin(), user.getLogin());
        User userUpdated = mapper.mapToUser(userDto);

        return mapper.mapToDto(service.saveUser(userUpdated), true);
    }

    public void fetchDelete(Long id) {
        User user = service.getUserOrException(id);
        validator.validateDefault(user);
        service.deleteUser(user);
    }
}
