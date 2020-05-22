package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTestSuite {

    @Autowired
    private UserMapper userMapper;

    @Before
    public void printBefore() {
        System.out.println("Test start ...");
    }

    @Test
    public void testUserFullClass() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .addLabBook(new LabBook())
                .build();
        // When

        // Then
        assertEquals(1L, user.getId());
        assertEquals("Jacek", user.getName());
        assertEquals("cos@interia.pl", user.getEmail());
        assertEquals("aaaa", user.getPassword());
        assertEquals(Role.ADMIN.name(), user.getRole());
        assertTrue(user.isBlocked());
        assertEquals(LocalDate.now(), user.getDate().toLocalDate());
        assertEquals(2, user.getLabBooks().size());
    }

    @Test
    public void testUserNotFullClass() {
        // Given
        User user = new User.UserBuilder()
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .build();
        // When

        // Then
        assertNull(user.getId());
        assertFalse(user.isBlocked());
        assertEquals(Role.USER.name(), user.getRole());
        assertEquals(LocalDate.now(), user.getDate().toLocalDate());
        assertEquals(0, user.getLabBooks().size());
    }

    @Test
    public void testUsersEquals() {
        // Given
        User user1 = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .addLabBook(new LabBook())
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
        // When
        boolean result = user1.equals(user2);

        // Then
        assertTrue(result);
    }

    @Test
    public void testUsersNotEquals() {
        // Given
        User user1 = new User.UserBuilder()
                .id(1L)
                .name("JacekB")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .addLabBook(new LabBook())
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
                .addLabBook(new LabBook())
                .build();
        // When
        boolean result = user1.equals(user2);

        // Then
        assertFalse(result);
    }

    @Test
    public void testMapToDto() {
        // Given
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .addLabBook(new LabBook())
                .build();

        // When
        UserDto userDto = userMapper.mapToDto(user, true);

        // Then
        assertEquals(1L, userDto.getId());
        assertEquals("Jacek", userDto.getName());
        assertEquals("cos@interia.pl", userDto.getEmail());
        assertEquals("****", userDto.getPassword());
        assertEquals(Role.ADMIN.name(), userDto.getRole());
        assertTrue(userDto.isBlocked());
    }

    @Test
    public void testMapToListDto() {
        LocalDateTime date = LocalDateTime.now();
        // Given
        User user1 = new User.UserBuilder()
                .id(1L)
                .name("Robert")
                .email("tmp@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(date)
                .build();
        User user2 = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("bbbb")
                .blocked(false)
                .role(Role.USER.name())
                .date(date)
                .build();
        List<User> lists = new ArrayList<>(Arrays.asList(user1, user2));

        // When
        List<UserDto> listDto = userMapper.mapToListDto(lists, true);

        // Then
        assertEquals(1L, listDto.get(0).getId());
        assertEquals("Robert", listDto.get(0).getName());
        assertEquals("tmp@interia.pl", listDto.get(0).getEmail());
        assertEquals("****", listDto.get(0).getPassword());
        assertEquals(Role.ADMIN.name(), listDto.get(0).getRole());
        assertTrue(listDto.get(0).isBlocked());
        assertEquals(date, listDto.get(0).getDate());
        assertEquals(1L, listDto.get(1).getId());
        assertEquals("Jacek", listDto.get(1).getName());
        assertEquals("cos@interia.pl", listDto.get(1).getEmail());
        assertEquals("****", listDto.get(1).getPassword());
        assertEquals(Role.USER.name(), listDto.get(1).getRole());
        assertFalse(listDto.get(1).isBlocked());
        assertEquals(date, listDto.get(1).getDate());
    }

    @Test
    public void testMapToUser() {
        // Given
        UserDto userDto = new UserDto(
                1L,
                "Jacek",
                "Brown",
                "jbr",
                "cos@interia.pl",
                "aaaa",
                true,
                true,
                Role.MODERATOR.name(),
                LocalDateTime.now());

        // When
        User user = userMapper.mapToUser(userDto);

        // Then
        assertEquals(1L, user.getId());
        assertEquals("Jacek", user.getName());
        assertEquals("Brown", user.getLastName());
        assertEquals("jbr", user.getLogin());
        assertEquals("cos@interia.pl", user.getEmail());
        assertEquals("aaaa", user.getPassword());
        assertEquals(Role.MODERATOR.name(), user.getRole());
        assertTrue(userDto.isBlocked());
        assertEquals(LocalDate.now(), user.getDate().toLocalDate());
        assertEquals(0, user.getLabBooks().size());
    }

    @Test
    public void testExistRole() {
        // Given
        String role1 = "ADMIN";
        String role2 = "ADMIn";

        // When
        boolean result1 = Role.exist(role1);
        boolean result2 = Role.exist(role2);

        // Then
        assertTrue(result1);
        assertFalse(result2);
    }

}
