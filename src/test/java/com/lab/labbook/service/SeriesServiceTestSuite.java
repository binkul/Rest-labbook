package com.lab.labbook.service;

import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.exception.EntityAlreadyExistsException;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.mapper.SeriesMapper;
import com.lab.labbook.repository.SeriesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SeriesServiceTestSuite {

    @InjectMocks
    private SeriesService seriesService;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private SeriesMapper seriesMapper;

    @Test
    public void testFindAll() {
        // Given
        Series series1 = new Series(1L, "title", new ArrayList<>());
        Series series2 = new Series(2L, "title II", new ArrayList<>());
        List<Series> series = Arrays.asList(series1, series2);
        SeriesDto seriesDto1 = new SeriesDto(1L, "title");
        SeriesDto seriesDto2 = new SeriesDto(2L, "title II");
        List<SeriesDto> seriesExts = Arrays.asList(seriesDto1, seriesDto2);

        // When
        when(seriesRepository.findAllByOrderByTitleAsc()).thenReturn(series);
        when(seriesMapper.mapToListDto(series)).thenReturn(seriesExts);
        List<SeriesDto> foundSeries = seriesService.getAll();

        // Then
        assertEquals(2, foundSeries.size());
        assertEquals(1L, foundSeries.get(0).getId());
        assertEquals(2L, foundSeries.get(1).getId());
        assertEquals("title", foundSeries.get(0).getTitle());
        assertEquals("title II", foundSeries.get(1).getTitle());
    }

    @Test
    public void testFindById() {
        // Given
        Series series = new Series(1L, "title", new ArrayList<>());
        SeriesDto seriesDto = new SeriesDto(1L, "title");

        // When
        when(seriesRepository.findById(1L)).thenReturn(java.util.Optional.of(series));
        when(seriesMapper.mapToDto(series)).thenReturn(seriesDto);
        SeriesDto foundSeries = seriesService.getById(1L);

        // Then
        assertEquals(1L, foundSeries.getId());
        assertEquals("title", foundSeries.getTitle());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testFindByIdException() {
        // Given
        Series series = new Series(1L, "title", new ArrayList<>());

        // When
        when(seriesRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        Series foundSeries = seriesService.getSeries(1L);

        // Then
    }

    @Test
    public void testFindAllByTitle() {
        // Given
        Series series1 = new Series(1L, "title", new ArrayList<>());
        Series series2 = new Series(2L, "title II", new ArrayList<>());
        List<Series> series = Arrays.asList(series1, series2);
        SeriesDto seriesDto1 = new SeriesDto(1L, "title");
        SeriesDto seriesDto2 = new SeriesDto(2L, "title II");
        List<SeriesDto> seriesExts = Arrays.asList(seriesDto1, seriesDto2);

        // When
        when(seriesRepository.findAllByTitleOrderByTitle("title")).thenReturn(series);
        when(seriesMapper.mapToListDto(series)).thenReturn(seriesExts);
        List<SeriesDto> foundSeries = seriesService.getByTitle("title");

        // Then
        assertEquals(2, foundSeries.size());
        assertEquals(1L, foundSeries.get(0).getId());
        assertEquals(2L, foundSeries.get(1).getId());
        assertEquals("title", foundSeries.get(0).getTitle());
        assertEquals("title II", foundSeries.get(1).getTitle());
    }

    @Test
    public void testCreateNewSeries() {
        // Given
        SeriesDto seriesDtoIn = new SeriesDto(1L, "title");
        Series series = new Series(1L, "title", new ArrayList<>());
        SeriesDto seriesDtoOut = new SeriesDto(1L, "title");

        // When
        when(seriesRepository.existsByTitle("title")).thenReturn(false);
        when(seriesMapper.mapToSeries(seriesDtoIn)).thenReturn(series);
        when(seriesRepository.save(series)).thenReturn(series);
        when(seriesMapper.mapToDto(series)).thenReturn(seriesDtoOut);
        SeriesDto foundSeries = seriesService.create(seriesDtoIn);

        // Then
        assertEquals(1L, foundSeries.getId());
        assertEquals("title", foundSeries.getTitle());
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testCreateException() {
        // Given
        SeriesDto seriesDto = new SeriesDto(1L, "title");

        // When
        when(seriesRepository.existsByTitle("title")).thenReturn(true);
        seriesService.create(seriesDto);

        // Then
    }

    @Test
    public void testUpdateSeries() {
        // Given
        SeriesDto seriesDto = new SeriesDto(1L, "title");
        Series series = new Series(1L, "title", new ArrayList<>());
        SeriesDto seriesExtDto = new SeriesDto(1L, "title");

        // When
        when(seriesRepository.findById(1L)).thenReturn(java.util.Optional.of(series));
        when(seriesRepository.existsByTitle("title")).thenReturn(false);
        when(seriesMapper.mapToSeries(seriesDto)).thenReturn(series);
        when(seriesRepository.save(series)).thenReturn(series);
        when(seriesMapper.mapToDto(series)).thenReturn(seriesExtDto);
        SeriesDto foundSeries = seriesService.update(seriesDto);

        // Then
        assertEquals(1L, foundSeries.getId());
        assertEquals("title", foundSeries.getTitle());
    }

}
