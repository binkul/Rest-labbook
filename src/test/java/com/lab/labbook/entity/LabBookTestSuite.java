package com.lab.labbook.entity;

import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.mapper.LabBookMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabBookTestSuite {

    private Series series;
    private User user;
    private LabBook labBook;

    @Autowired
    private LabBookMapper labBookMapper;

    @Before
    public void prepareLabBook() {
        this.series = new Series(1l, "none", new ArrayList<>());
        this.user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        this.labBook = new LabBook.LabBuilder()
                .id(1L)
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.546))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("CREATED")
                .user(user)
                .series(series)
                .build();
    }

    @Test
    public void testLabBookFullClass() {
        // Given When

        // Then
        assertEquals(1L, labBook.getId());
        assertEquals("New one", labBook.getTitle());
        assertEquals("New experiment", labBook.getDescription());
        assertEquals("No influence on air pollution", labBook.getConclusion());
        assertEquals( BigDecimal.valueOf(1.546), labBook.getDensity());
        assertEquals(LocalDate.now(), labBook.getCreationDate().toLocalDate());
        assertEquals(LocalDate.now(), labBook.getUpdateDate().toLocalDate());
        assertEquals(Status.CREATED.name(), labBook.getStatus());
        assertEquals(user, labBook.getUser());
        assertEquals(series, labBook.getSeries());
        assertEquals(0, labBook.getIngredient().size());
    }

    @Test
    public void testLabBookDefaultValueClass() {
        // Given
        LabBook labBook = new LabBook.LabBuilder()
                .id(1L)
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .user(user)
                .series(series)
                .build();
        // When

        // Then
        assertEquals(1L, labBook.getId());
        assertEquals("New one", labBook.getTitle());
        assertEquals("New experiment", labBook.getDescription());
        assertEquals("No influence on air pollution", labBook.getConclusion());
        assertEquals( BigDecimal.valueOf(1), labBook.getDensity());
        assertEquals(LocalDate.now(), labBook.getCreationDate().toLocalDate());
        assertEquals(LocalDate.now(), labBook.getUpdateDate().toLocalDate());
        assertEquals(Status.CREATED.name(), labBook.getStatus());
        assertEquals(user, labBook.getUser());
        assertEquals(series, labBook.getSeries());
        assertEquals(0, labBook.getIngredient().size());
    }

    @Test
    public void testMapToExtDto() {
        // Given When
        LabBookExtDto mapped = labBookMapper.mapToExtDto(labBook);

        // Then
        assertEquals(1L, mapped.getId());
        assertEquals("New one", mapped.getTitle());
        assertEquals("New experiment", mapped.getDescription());
        assertEquals("No influence on air pollution", mapped.getConclusion());
        assertEquals( BigDecimal.valueOf(1.546), mapped.getDensity());
        assertEquals(labBook.getCreationDate(), mapped.getCreatedDate());
        assertEquals(labBook.getUpdateDate(), mapped.getUpdatedDate());
        assertEquals(Status.CREATED.name(), mapped.getStatus());
        assertEquals(user.getId(), mapped.getUserId());
        assertEquals(series.getId(), mapped.getSeriesId());
    }

    @Test
    public void testMapToExtDtoList() {
        // Given When
        List<LabBook> labBooks = Arrays.asList(labBook, labBook);
        List<LabBookExtDto> mapped = labBookMapper.mapToExtListDto(labBooks);

        // Then
        assertEquals(1L, mapped.get(0).getId());
        assertEquals("New one", mapped.get(0).getTitle());
        assertEquals("New experiment", mapped.get(0).getDescription());
        assertEquals("No influence on air pollution", mapped.get(0).getConclusion());
        assertEquals( BigDecimal.valueOf(1.546), mapped.get(0).getDensity());
        assertEquals(labBook.getCreationDate(), mapped.get(0).getCreatedDate());
        assertEquals(labBook.getUpdateDate(), mapped.get(0).getUpdatedDate());
        assertEquals(Status.CREATED.name(), mapped.get(0).getStatus());
        assertEquals(user.getId(), mapped.get(0).getUserId());
        assertEquals(series.getId(), mapped.get(0).getSeriesId());
    }

}
