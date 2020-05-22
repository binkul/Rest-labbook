package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.mapper.SeriesMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeriesTestSuite {

    @Autowired
    private SeriesMapper seriesMapper;

    @Test
    public void testMapToSeriesDto() {
        // Given
        Series series = new Series(1L, "any", new ArrayList<>());

        // When
        SeriesDto seriesDto = seriesMapper.mapToDto(series);

        // Then
        assertEquals(1L, seriesDto.getId());
        assertEquals("any", seriesDto.getTitle());
    }

    @Test
    public void testMapToSeriesListDto() {
        // Given
        Series series1 = new Series(1L, "any", new ArrayList<>());
        Series series2 = new Series(2L, "many", new ArrayList<>());
        List<Series> list = Arrays.asList(series1, series2);

        // When
        List<SeriesDto> seriesList = seriesMapper.mapToListDto(list);
        SeriesDto seriesDto1 = seriesList.get(0);
        SeriesDto seriesDto2 = seriesList.get(1);

        // Then
        assertEquals(2, seriesList.size());
        assertEquals(1, seriesDto1.getId());
        assertEquals("any", seriesDto1.getTitle());
        assertEquals(2, seriesDto2.getId());
        assertEquals("many", seriesDto2.getTitle());
    }

    @Test
    public void testMapToSeries() {
        // Given
        SeriesDto seriesDto = new SeriesDto(1L, "any");

        // When
        Series series = seriesMapper.mapToSeries(seriesDto);

        // Then
        assertEquals(1L, series.getId());
        assertEquals("any", series.getTitle());
    }

}
