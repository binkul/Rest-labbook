package com.lab.labbook.validator;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.User;
import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserValidatorTestSuite {

    @InjectMocks
    UserValidator userValidator;

    @Mock
    UserRepository userRepository;

    @Mock
    DataConfig dataConfig;

    @Test(expected = EntityNotFoundException.class)
    public void testValidateRole() {
        // Given
        String roleName = "ADMIn";

        // When Then
        userValidator.validateRole(roleName);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testValidateLogin() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .lastName("Smith")
                .email("cos@interia.pl")
                .login("jbr")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();

        // When Then
        when(userRepository.existsByLogin("jbr")).thenReturn(true);
        userValidator.validateLogin(user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testValidateUser() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .lastName("Smith")
                .email("cos@interia.pl")
                .login("jbr")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();

        // When Then
        when(userRepository.existsById(1L)).thenReturn(false);
        userValidator.validateUser(1L);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testValidateEmail() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();

        // When Then
        when(userRepository.existsByEmail("cos@interia.pl")).thenReturn(true);
        userValidator.validateEmail(user);
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testValidateDefault() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Default")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();

        // When Then
        when(dataConfig.getDefaultUser()).thenReturn("Default");
        userValidator.validateDefault(user);
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testValidateNameChange() {
        // Given

        // When Then
        userValidator.validateLoginUpdate("Jacek", "Robert");
    }
}
