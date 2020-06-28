package com.lab.labbook.repository;

import com.lab.labbook.config.ApplicationConfig;
import com.lab.labbook.config.DataConfig;
import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {
    private Long id1;
    private Long id2;
    private User user2;

    @Autowired
    UserRepository repository;

    @Autowired
    DataConfig config;

    @Before
    public void prepareDataBase() {
        User user1 = new User.UserBuilder()
                .name("Rob")
                .lastName("Brown")
                .login("jbr")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .addLabBook(new LabBook())
                .build();
        user2 = new User.UserBuilder()
                .name("Robert")
                .lastName("Smith")
                .login("rs45")
                .email("tmp@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .addLabBook(new LabBook())
                .build();
        repository.save(user1);
        repository.save(user2);
        id1 = user1.getId();
        id2 = user2.getId();
    }

    @After
    public void cleanDataBase() {
        repository.deleteById(id1);
        repository.deleteById(id2);
    }

    @Test
    public void testGetAllUsers() {
        // Given

        // When Then (also default user)
        assertEquals(3, repository.findAll().size());
    }

    @Test
    public void testFindUserByLogin() {
        // Given

        // When
        Optional<User> found = repository.findByLogin("rs45");

        // Then (also default user)
        assertTrue(found.isPresent());
        assertEquals(user2, found.get());
    }

    @Test
    public void testExistsUserByLogin() {
        // Given

        // When
        boolean found1 = repository.existsByLogin("jbr");
        boolean found2 = repository.existsByLogin("Rob");
        boolean found3 = repository.existsByLogin(config.getDefaultUser());

        // Then (also default user)
        assertTrue(found1);
        assertFalse(found2);
        assertTrue(found3);
    }

    @Test
    public void testExistsUserByEmail() {
        // Given

        // When
        boolean found1 = repository.existsByEmail("cos@interia.pl");
        boolean found2 = repository.existsByEmail("not@interia.pl");

        // Then (also default user)
        assertTrue(found1);
        assertFalse(found2);
    }

    @Test
    public void testFindAllByName() {
        // Given

        // When
        List<User> users = repository.findAllByName("Rob");

        // Then
        assertEquals(2, users.size());
    }

    @Test
    public void testFindAllByNameII() {
        // Given

        // When
        List<User> users = repository.findAllByName("Robe");

        // Then
        assertEquals(1, users.size());
        assertEquals("Robert", users.get(0).getName());
        assertEquals("Smith", users.get(0).getLastName());
        assertEquals("rs45", users.get(0).getLogin());
        assertEquals("tmp@interia.pl", users.get(0).getEmail());
        assertEquals("aaaa", users.get(0).getPassword());
        assertEquals("ADMIN", users.get(0).getRole());
    }
}
