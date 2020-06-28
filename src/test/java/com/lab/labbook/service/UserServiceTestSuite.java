package com.lab.labbook.service;

import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.User;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTestSuite {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LabBookRepository labBookRepository;

    @Mock
    private DataConfig dataConfig;

    @Test
    public void shouldSaveUserTest() {
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

        // When
        when(userRepository.save(user)).thenReturn(user);
        User saved = userService.saveUser(user);

        // Then
        Assertions.assertEquals(1L, saved.getId());
        Assertions.assertEquals("Jacek", saved.getName());
        Assertions.assertEquals("cos@interia.pl", saved.getEmail());
        Assertions.assertEquals("aaaa", saved.getPassword());
        Assertions.assertEquals(Role.ADMIN.name(), saved.getRole());
        assertTrue(saved.isBlocked());
        Assertions.assertEquals(LocalDate.now(), saved.getDate().toLocalDate());
        Assertions.assertEquals(0, saved.getLabBooks().size());
    }

    @Test
    public void shouldGetUserListTest() {
        // Given
        User user1 = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("bbbb")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        User user2 = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .build();
        List<User> users = Arrays.asList(user1, user2);

        // When
        when(userRepository.findAll()).thenReturn(users);
        List<User> founded = userService.getUsers();

        // Then
        assertEquals(2, users.size());
        assertEquals("bbbb", users.get(0).getPassword());
    }

    @Test
    public void shouldGetEmptyUserList() {
        // Given
        List<User> users = new ArrayList<>();

        // When
        when(userRepository.findAll()).thenReturn(users);
        List<User> founded = userService.getUsers();

        // Then
        assertEquals(0, founded.size());
    }


    @Test
    public void shouldGetUserByIdTest() {
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

        // When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User founded = userService.getById(1L);

        // Then
        Assertions.assertEquals(1L, founded.getId());
        Assertions.assertEquals("Jacek", founded.getName());
        Assertions.assertEquals("cos@interia.pl", founded.getEmail());
        Assertions.assertEquals("aaaa", founded.getPassword());
        Assertions.assertEquals(Role.ADMIN.name(), founded.getRole());
        assertTrue(founded.isBlocked());
        Assertions.assertEquals(LocalDate.now(), founded.getDate().toLocalDate());
        Assertions.assertEquals(0, founded.getLabBooks().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldGetEmptyUserTest() throws EntityNotFoundException {
        // Given

        // When & Then
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        User founded = userService.getById(1L);
    }

    @Test
    public void shouldGetUserByLoginTest() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .lastName("Smith")
                .email("cos@interia.pl")
                .login("jsm")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();

        // When
        when(userRepository.findByLogin("jsm")).thenReturn(Optional.of(user));
        User founded = userService.getByLogin("jsm");

        // Then
        Assertions.assertEquals(1L, founded.getId());
        Assertions.assertEquals("Jacek", founded.getName());
        Assertions.assertEquals("cos@interia.pl", founded.getEmail());
        Assertions.assertEquals("aaaa", founded.getPassword());
        Assertions.assertEquals(Role.ADMIN.name(), founded.getRole());
        assertTrue(founded.isBlocked());
        Assertions.assertEquals(LocalDate.now(), founded.getDate().toLocalDate());
        Assertions.assertEquals(0, founded.getLabBooks().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldGetEmptyUserByNameTest() throws EntityNotFoundException {
        // Given

        // When & Then
        when(userRepository.findByLogin("Jacek")).thenReturn(Optional.empty());
        User founded = userService.getByLogin("Jacek");
    }

    @Test
    public void shouldDeleteUser() {
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
        User defaultUser = new User.UserBuilder()
                .id(1L)
                .name("default")
                .lastName("default")
                .email("tmp")
                .login("default")
                .password("bbbb")
                .blocked(false)
                .role(Role.USER.name())
                .date(LocalDateTime.now())
                .build();

        // When
        when(labBookRepository.existsByUser(user)).thenReturn(true);
        when(dataConfig.getDefaultUser()).thenReturn("Default");
        when(userRepository.findByLogin("Default")).thenReturn(Optional.of(defaultUser));
        userService.deleteUser(user);

        // Then
        verify(labBookRepository, times(1)).updateUserId(user, defaultUser);
    }

}
