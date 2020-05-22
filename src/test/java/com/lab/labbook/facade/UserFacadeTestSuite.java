package com.lab.labbook.facade;

import com.lab.labbook.entity.User;
import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.mapper.UserMapper;
import com.lab.labbook.service.UserService;
import com.lab.labbook.validator.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserFacadeTestSuite {

    @InjectMocks
    private UserFacade facade;

    @Mock
    private UserMapper mapper;

    @Mock
    private UserService service;

    @Test
    public void testFetchGetUsers() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "jacek",
                "smith",
                "jsm34",
                "none",
                "1234",
                false,
                true,
                "USER",
                date
        );
        List<UserDto> lists = new ArrayList<>(Collections.singletonList(userDto));
        List<User> users = new ArrayList<>();

        // When
        when(service.getUsers()).thenReturn(users);
        when(mapper.mapToListDto(users, true)).thenReturn(lists);
        List<UserDto> result = facade.fetchGetUsers();

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("jacek", result.get(0).getName());
        assertEquals("smith", result.get(0).getLastName());
        assertEquals("jsm34", result.get(0).getLogin());
        assertEquals("none", result.get(0).getEmail());
        assertEquals("1234", result.get(0).getPassword());
        assertFalse(result.get(0).isBlocked());
        assertEquals("USER", result.get(0).getRole());
        assertEquals(date, result.get(0).getDate());
    }

    @Test
    public void testFetchEmptyList() {
        // Given
        List<UserDto> lists = new ArrayList<>();
        List<User> users = new ArrayList<>();

        // When
        when(service.getUsers()).thenReturn(users);
        when(mapper.mapToListDto(users, true)).thenReturn(lists);
        List<UserDto> result = facade.fetchGetUsers();

        // Then
        assertEquals(0, result.size());
    }

    @Test
    public void testFetchGetByName() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "jacek",
                "smith",
                "jsm34",
                "none",
                "1234",
                false,
                true,
                "USER",
                date
        );
        List<UserDto> lists = new ArrayList<>(Collections.singletonList(userDto));
        List<User> users = new ArrayList<>();

        // When
        when(service.getByName("jacek")).thenReturn(users);
        when(mapper.mapToListDto(users, true)).thenReturn(lists);
        List<UserDto> result = facade.fetchGetByName("jacek");

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("jacek", result.get(0).getName());
        assertEquals("smith", result.get(0).getLastName());
        assertEquals("jsm34", result.get(0).getLogin());
        assertEquals("none", result.get(0).getEmail());
        assertEquals("1234", result.get(0).getPassword());
        assertFalse(result.get(0).isBlocked());
        assertEquals("USER", result.get(0).getRole());
        assertEquals(date, result.get(0).getDate());
    }

    @Test
    public void testFetchGetById() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "jacek",
                "smith",
                "jsm34",
                "none",
                "1234",
                false,
                true,
                "USER",
                date
        );
        User user = new User();

        // When
        when(service.getById(1L)).thenReturn(user);
        when(mapper.mapToDto(user, true)).thenReturn(userDto);
        UserDto result = facade.fetchGetById(1L, true);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("jacek", result.getName());
        assertEquals("smith", result.getLastName());
        assertEquals("jsm34", result.getLogin());
        assertEquals("none", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertFalse(result.isBlocked());
        assertEquals("USER", result.getRole());
        assertEquals(date, result.getDate());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFetchGetByIdException() {
        // Given

        // When Then
        when(service.getById(1L)).thenThrow(new EntityNotFoundException(ExceptionType.USER_NOT_FOUND, ""));
        UserDto result = facade.fetchGetById(1L, true);
    }

    @Test
    public void testFetchGetByLogin() {
        // Given
        LocalDateTime date = LocalDateTime.now();
        UserDto userDto = new UserDto(
                1L,
                "jacek",
                "smith",
                "jsm34",
                "none",
                "1234",
                false,
                true,
                "USER",
                date
        );
        User user = new User();

        // When
        when(service.getByLogin("jsm34")).thenReturn(user);
        when(mapper.mapToDto(user, false)).thenReturn(userDto);
        UserDto result = facade.fetchGetByLogin("jsm34");

        // Then
        assertEquals(1L, result.getId());
        assertEquals("jacek", result.getName());
        assertEquals("smith", result.getLastName());
        assertEquals("jsm34", result.getLogin());
        assertEquals("none", result.getEmail());
        assertEquals("1234", result.getPassword());
        assertFalse(result.isBlocked());
        assertEquals("USER", result.getRole());
        assertEquals(date, result.getDate());
    }
}
