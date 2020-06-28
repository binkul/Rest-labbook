package com.lab.labbook.repository;

import com.lab.labbook.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabBookRepositoryTestSuite {

    private User user;
    private LabBook labBookA;
    private LabBook labBookB;
    private Series series;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private LabBookRepository labBookRepository;

    @Before
    public void prepareData() {

        series = new Series("series");
        seriesRepository.save(series);

        user = new User.UserBuilder()
                .name("Rob")
                .lastName("Brown")
                .login("jbr")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        userRepository.save(user);

        labBookA = new LabBook.LabBuilder()
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.5460))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("CREATED")
                .user(user)
                .series(series)
                .build();
        labBookB = new LabBook.LabBuilder()
                .title("New two")
                .description("Old experiment")
                .conclusion("none")
                .density(BigDecimal.valueOf(2.0000))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("DELETED")
                .user(user)
                .series(series)
                .build();
        labBookRepository.save(labBookA);
        labBookRepository.save(labBookB);
    }

    @After
    public void cleanData() {
        labBookRepository.delete(labBookA);
        labBookRepository.delete(labBookB);
        seriesRepository.delete(series);
        userRepository.delete(user);
    }

    @Test
    public void testFindAllByTitleOrderById() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByTitleOrderById("one");

        // Then
        assertEquals(1, labBooks.size());
        assertEquals("New one", labBooks.get(0).getTitle());
    }

    @Test
    public void testFindAllByTitleAndStatusNotLikeOrderById() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByTitleAndStatusNotLikeOrderById("two", "CREATED");

        // Then
        assertEquals(1, labBooks.size());
        assertEquals("New two", labBooks.get(0).getTitle());
    }

    @Test
    public void testFindAllByTitleAndStatusNotLikeOrderByIdEmptyList() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByTitleAndStatusNotLikeOrderById("two", "DELETED");

        // Then
        assertEquals(0, labBooks.size());
    }

    @Test
    public void testFindAllByUserAndTitleAndStatusNotLikeOrderById() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByUserAndTitleAndStatusNotLikeOrderById(user, "two", "CREATED");

        // Then
        assertEquals(1, labBooks.size());
        assertEquals("New two", labBooks.get(0).getTitle());
    }

    @Test
    public void testFindAllByUserAndTitleAndStatusNotLikeOrderByIdEmptyList() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByUserAndTitleAndStatusNotLikeOrderById(user, "three", "CREATED");

        // Then
        assertEquals(0, labBooks.size());
    }

    @Test
    public void testExistsByUser() {
        // Given

        // When
        boolean found = labBookRepository.existsByUser(user);

        // Then
        assertTrue(found);
    }

    @Test
    public void testExistsBySeries() {
        // Given

        // When
        boolean found = labBookRepository.existsBySeries(series);

        // Then
        assertTrue(found);
    }

    @Test
    public void testCountByStatus() {
        // Given

        // When
        int found = labBookRepository.countByStatus("DELETED");

        // Then
        assertEquals(1, found);
    }

    @Test
    public void testFindAllByOrderById() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findAllByOrderById();

        // Then
        assertEquals(2, labBooks.size());
        assertEquals("New one", labBooks.get(0).getTitle());
        assertEquals("New two", labBooks.get(1).getTitle());
    }

    @Test
    public void testFindByUserAndStatusIsNotOrderById() {
        // Given

        // When
        List<LabBook> labBooks = labBookRepository.findByUserAndStatusIsNotOrderById(user, "DELETED");

        // Then
        assertEquals(1, labBooks.size());
        assertEquals("New one", labBooks.get(0).getTitle());
    }
}
