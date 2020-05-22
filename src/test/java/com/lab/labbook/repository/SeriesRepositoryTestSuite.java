package com.lab.labbook.repository;

import com.lab.labbook.entity.Series;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeriesRepositoryTestSuite {
    private Long id1;
    private Long id2;
    private Series series2;

    @Autowired
    SeriesRepository repository;

    @Before
    public void prepareDataBase() {
        Series series1 = new Series("One");
        series2 = new Series("Two");
        repository.save(series1);
        repository.save(series2);
        id1 = series1.getId();
        id2 = series2.getId();
    }

    @After
    public void cleanDataBase() {
        repository.deleteById(id1);
        repository.deleteById(id2);
    }

    @Test
    public void testFindByTitle() {
        // Given

        // When
        Optional<Series> founded = repository.findByTitle("Two");

        // Then
        assertTrue(founded.isPresent());
        assertEquals(series2, founded.get());
    }

    @Test
    public void testExistByTitle() {
        // Given

        // When
        boolean founded = repository.existsByTitle("One");
        boolean notFounded = repository.existsByTitle("Three");

        // Then
        assertTrue(founded);
        assertFalse(notFounded);
    }

    @Test
    public void testFindAll() {
        // Given

        // When
        List<Series> series = repository.findAllByOrderByTitleAsc();

        // Then
        assertEquals(3, series.size());
        assertEquals("One", series.get(0).getTitle());
        assertEquals("Two", series.get(1).getTitle());
        assertEquals("unbound", series.get(2).getTitle());
    }

    @Test
    public void testFindAllByTitleOrderByTitle() {
        // Given

        // When
        List<Series> series = repository.findAllByTitleOrderByTitle("o");
        List<Series> series1 = repository.findAllByTitleOrderByTitle("a");

        // Then
        assertEquals(1, series.size());
        assertEquals("One", series.get(0).getTitle());
        assertEquals(0, series1.size());
    }

}
