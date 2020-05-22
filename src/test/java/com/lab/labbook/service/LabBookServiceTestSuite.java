package com.lab.labbook.service;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.User;
import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.mapper.LabBookMapper;
import com.lab.labbook.repository.LabBookRepository;
import com.lab.labbook.validator.LabBookValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LabBookServiceTestSuite {

    private Series series;
    private User user;
    private User userModerator;
    private LabBook labBook;
    private LabBookExtDto labBookExtDto;
    private LocalDateTime date;

    @InjectMocks
    private LabBookService service;

    @Mock
    private LabBookRepository repository;

    @Mock
    private LabBookMapper mapper;

    @Mock
    private UserService userService;

    @Mock
    private SeriesService seriesService;

    @Mock
    private LabBookValidator validator;

    @Before
    public void prepareLabBook() {
        this.date = LocalDateTime.now();
        this.series = new Series(1l, "none", new ArrayList<>());
        this.user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.USER.name())
                .date(LocalDateTime.now())
                .build();
        this.userModerator = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.MODERATOR.name())
                .date(LocalDateTime.now())
                .build();
        this.labBook = new LabBook.LabBuilder()
                .id(1L)
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.55))
                .creationDate(date)
                .updateDate(date)
                .status("CREATED")
                .user(user)
                .series(series)
                .build();
        this.labBookExtDto = new LabBookExtDto.LabExtBuilder()
                .id(labBook.getId())
                .title(labBook.getTitle())
                .description(labBook.getDescription())
                .conclusion(labBook.getConclusion())
                .density(labBook.getDensity())
                .creationDate(labBook.getCreationDate())
                .updateDate(labBook.getUpdateDate())
                .status(labBook.getStatus())
                .userId(labBook.getUser().getId())
                .seriesId(labBook.getSeries().getId())
                .build();
    }

    @Test
    public void testGetAllLabbooks() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        labBookExtDtos.add(labBookExtDto);
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(repository.findAllByOrderById()).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getAll();

        // Then
        assertEquals(1, found.size());
        assertEquals("New one", found.get(0).getTitle());
        assertEquals("New experiment", found.get(0).getDescription());
        assertEquals("No influence on air pollution", found.get(0).getConclusion());
        assertEquals(new BigDecimal("1.55"), found.get(0).getDensity());
        assertEquals(date, found.get(0).getCreatedDate());
        assertEquals(date, found.get(0).getUpdatedDate());
        assertEquals("CREATED", found.get(0).getStatus());
        assertEquals(1L, found.get(0).getUserId());
        assertEquals(1L, found.get(0).getSeriesId());
    }

    @Test
    public void testGetAllLabbooksEmptyList() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(repository.findAllByOrderById()).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getAll();

        // Then
        assertEquals(0, found.size());
    }

    @Test
    public void testGetByTitle() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        labBookExtDtos.add(labBookExtDto);
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(repository.findAllByTitleOrderById("New")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByTitle("New");

        // Then
        assertEquals(1, found.size());
        assertEquals("New one", found.get(0).getTitle());
        assertEquals("New experiment", found.get(0).getDescription());
        assertEquals("No influence on air pollution", found.get(0).getConclusion());
        assertEquals(new BigDecimal("1.55"), found.get(0).getDensity());
        assertEquals(date, found.get(0).getCreatedDate());
        assertEquals(date, found.get(0).getUpdatedDate());
        assertEquals("CREATED", found.get(0).getStatus());
        assertEquals(1L, found.get(0).getUserId());
        assertEquals(1L, found.get(0).getSeriesId());
    }

    @Test
    public void testGetByTitleEmptyList() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(repository.findAllByTitleOrderById("New")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByTitle("New");

        // Then
        assertEquals(0, found.size());
    }

    @Test
    public void testGetByUser() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        labBookExtDtos.add(labBookExtDto);
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(userService.getById(1L)).thenReturn(user);
        when(repository.findByUserAndStatusIsNotOrderById(user, "DELETED")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByUser(1L);

        // Then
        assertEquals(1, found.size());
        assertEquals("New one", found.get(0).getTitle());
        assertEquals("New experiment", found.get(0).getDescription());
        assertEquals("No influence on air pollution", found.get(0).getConclusion());
        assertEquals(new BigDecimal("1.55"), found.get(0).getDensity());
        assertEquals(date, found.get(0).getCreatedDate());
        assertEquals(date, found.get(0).getUpdatedDate());
        assertEquals("CREATED", found.get(0).getStatus());
        assertEquals(1L, found.get(0).getUserId());
        assertEquals(1L, found.get(0).getSeriesId());
    }

    @Test
    public void testGetByUserEmptyList() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(userService.getById(1L)).thenReturn(user);
        when(repository.findByUserAndStatusIsNotOrderById(user, "DELETED")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByUser(1L);

        // Then
        assertEquals(0, found.size());
    }

    @Test
    public void testGetByUserAndTitle() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        labBookExtDtos.add(labBookExtDto);
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(userService.getById(1L)).thenReturn(user);
        when(repository.findAllByUserAndTitleAndStatusNotLikeOrderById(user, "New", "DELETED")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByUserAndTitle(1L, "New");

        // Then
        assertEquals(1, found.size());
        assertEquals("New one", found.get(0).getTitle());
        assertEquals("New experiment", found.get(0).getDescription());
        assertEquals("No influence on air pollution", found.get(0).getConclusion());
        assertEquals(new BigDecimal("1.55"), found.get(0).getDensity());
        assertEquals(date, found.get(0).getCreatedDate());
        assertEquals(date, found.get(0).getUpdatedDate());
        assertEquals("CREATED", found.get(0).getStatus());
        assertEquals(1L, found.get(0).getUserId());
        assertEquals(1L, found.get(0).getSeriesId());
    }

    @Test
    public void testGetByUserAndTitleII() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        labBookExtDtos.add(labBookExtDto);
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(userService.getById(1L)).thenReturn(userModerator);
        when(repository.findAllByTitleAndStatusNotLikeOrderById( "New", "DELETED")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByUserAndTitle(1L, "New");

        // Then
        assertEquals(1, found.size());
        assertEquals("New one", found.get(0).getTitle());
        assertEquals("New experiment", found.get(0).getDescription());
        assertEquals("No influence on air pollution", found.get(0).getConclusion());
        assertEquals(new BigDecimal("1.55"), found.get(0).getDensity());
        assertEquals(date, found.get(0).getCreatedDate());
        assertEquals(date, found.get(0).getUpdatedDate());
        assertEquals("CREATED", found.get(0).getStatus());
        assertEquals(1L, found.get(0).getUserId());
        assertEquals(1L, found.get(0).getSeriesId());
    }

    @Test
    public void testGetByUserAndTitleEmptyList() {
        // Given
        List<LabBookExtDto> labBookExtDtos = new ArrayList<>();
        List<LabBook> labBooks = new ArrayList<>();
        labBooks.add(labBook);

        // When
        when(userService.getById(1L)).thenReturn(user);
        when(repository.findAllByUserAndTitleAndStatusNotLikeOrderById(user, "New", "DELETED")).thenReturn(labBooks);
        when(mapper.mapToExtListDto(labBooks)).thenReturn(labBookExtDtos);
        List<LabBookExtDto> found = service.getByUserAndTitle(1L, "New");

        // Then
        assertEquals(0, found.size());
    }

    @Test
    public void testGetLabbook() {
        // Given

        // When
        when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(labBook));
        LabBook found = service.getLabBook(1L);

        // Then
        assertEquals("New one", found.getTitle());
        assertEquals("New experiment", found.getDescription());
        assertEquals("No influence on air pollution", found.getConclusion());
        assertEquals(new BigDecimal("1.55"), found.getDensity());
        assertEquals(date, found.getCreationDate());
        assertEquals(date, found.getUpdateDate());
        assertEquals("CREATED", found.getStatus());
        assertEquals(user, found.getUser());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetLabbookException() {
        // Given

        // When Then
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());
        LabBook found = service.getLabBook(1L);
    }

    @Test
    public void testGetLabbookById() {
        // Given

        // When
        when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(labBook));
        when(mapper.mapToExtDto(labBook)).thenReturn(labBookExtDto);
        LabBookExtDto found = service.getById(1L);

        // Then
        assertEquals("New one", found.getTitle());
        assertEquals("New experiment", found.getDescription());
        assertEquals("No influence on air pollution", found.getConclusion());
        assertEquals(new BigDecimal("1.55"), found.getDensity());
        assertEquals(date, found.getCreatedDate());
        assertEquals(date, found.getUpdatedDate());
        assertEquals("CREATED", found.getStatus());
        assertEquals(1L, found.getUserId());
        assertEquals(1L, found.getSeriesId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetLabbookByIdException() {
        // Given

        // When Then
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());
        LabBookExtDto found = service.getById(1L);
    }

    @Test
    public void testUpdateLabbook() {
        // Given
        Map<String, String> updates = new HashMap<>();
        updates.put("title", "none");
        updates.put("description", "none");
        updates.put("conclusion", "none");
        updates.put("density", "1.67");
        updates.put("seriesId", "1");

        // When
        when(repository.findById(1L)).thenReturn(java.util.Optional.ofNullable(labBook));
        when(validator.validateDensity(new BigDecimal("1.67"))).thenReturn(new BigDecimal("1.67"));
        when(seriesService.getSeries(1L)).thenReturn(series);
        when(repository.save(labBook)).thenReturn(labBook);
        when(mapper.mapToExtDto(labBook)).thenReturn(labBookExtDto);
        LabBookExtDto found = service.update(updates, 1L);

        // Then
        assertEquals("New one", found.getTitle());
        assertEquals("New experiment", found.getDescription());
        assertEquals("No influence on air pollution", found.getConclusion());
        assertEquals(new BigDecimal("1.55"), found.getDensity());
        assertEquals(date, found.getCreatedDate());
        assertEquals(date, found.getUpdatedDate());
        assertEquals("CREATED", found.getStatus());
        assertEquals(1L, found.getUserId());
        assertEquals(1L, found.getSeriesId());
    }
}
