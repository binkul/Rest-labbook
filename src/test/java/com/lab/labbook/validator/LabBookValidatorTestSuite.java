package com.lab.labbook.validator;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.User;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.repository.SeriesRepository;
import com.lab.labbook.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabBookValidatorTestSuite {

    @Autowired
    private LabBookValidator validator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Test
    public void testValidateLabBook() {
        // Given
        Series series = new Series("any");
        User user = new User.UserBuilder()
                .name("Jacek")
                .lastName("Smith")
                .email("cos@interia.pl")
                .login("jbr4")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        userRepository.save(user);
        seriesRepository.save(series);
        LabBookDto labBookDto = new LabBookDto(
                1L,
                "Test title",
                "Description",
                "Conclusion",
                new BigDecimal("1.1"),
                user.getId(),
                series.getId());

        // When
        LabBook labBook = validator.validateLabBook(labBookDto);

        // Then
        assertEquals("Test title", labBook.getTitle());
        assertEquals("Description", labBook.getDescription());
        assertEquals("Conclusion", labBook.getConclusion());
        assertEquals(new BigDecimal("1.1"), labBook.getDensity());
        assertEquals(user, labBook.getUser());
        assertEquals(series, labBook.getSeries());

        // Clean
        userRepository.delete(user);
        seriesRepository.delete(series);
    }

    @Test
    public void testValidateLabBookNullDensity() {
        // Given
        Series series = new Series("any");
        User user = new User.UserBuilder()
                .name("Jacek")
                .lastName("Smith")
                .email("cos@interia.pl")
                .login("jbr4")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        userRepository.save(user);
        seriesRepository.save(series);
        LabBookDto labBookDto = new LabBookDto(
                1L,
                "Test title",
                "Description",
                "Conclusion",
                null,
                user.getId(),
                series.getId());

        // When
        LabBook labBook = validator.validateLabBook(labBookDto);

        // Then
        assertEquals("Test title", labBook.getTitle());
        assertEquals("Description", labBook.getDescription());
        assertEquals("Conclusion", labBook.getConclusion());
        assertEquals(BigDecimal.ONE, labBook.getDensity());
        assertEquals(user, labBook.getUser());
        assertEquals(series, labBook.getSeries());

        // Clean
        userRepository.delete(user);
        seriesRepository.delete(series);
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testValidateLabBookException() {
        // Given
        LabBookDto labBookDto = new LabBookDto(
                1L,
                "",
                "Description",
                "Conclusion",
                null,
                1L,
                1L);

        // When Then
        validator.validateLabBook(labBookDto);
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testValidateLabBookDeleted() {
        // Given
        LabBook labBook = new LabBook.LabBuilder()
                .id(1L)
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.546))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("DELETED")
                .user(null)
                .series(null)
                .build();

        // When Then
        validator.validateDeleted(labBook);
    }
}
