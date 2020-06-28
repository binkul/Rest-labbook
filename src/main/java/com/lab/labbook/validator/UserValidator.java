package com.lab.labbook.validator;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.User;
import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final DataConfig dataConfig;

    public void validateRole(String role) {
        if (!Role.exist(role)) {
            throw new EntityNotFoundException(ExceptionType.ROLE_NOT_FOUND, role);
        }
    }

    public void validateUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, id.toString());
        }
    }

    public void validateLogin(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new EntityAlreadyExistsException(ExceptionType.USER_FOUND, user.getLogin());
        }
    }

    public void validateEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException(ExceptionType.USER_EMAIL_FOUND, user.getEmail());
        }
    }

    public void validateLoginUpdate(String newLogin, String oldLogin) {
        if (!newLogin.equals(oldLogin)) {
            throw new ForbiddenOperationException(ExceptionType.USER_CHANGE_NAME, newLogin);
        }
    }

    public void validateDefault(User user) {
        if (user.getName().equals(dataConfig.getDefaultUser())) {
            throw new ForbiddenOperationException(ExceptionType.USER_DEFAULT_CHANGE, "");
        }
    }
}
