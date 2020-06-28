package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.dto.SeriesDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.service.SeriesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SeriesController.class)
public class SeriesControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeriesService seriesService;

    @Test
    public void testGetAllSeries() throws Exception {
        // Given
        SeriesDto seriesDto1 = new SeriesDto(1L, "none1");
        SeriesDto seriesDto2 = new SeriesDto(2L, "none2");
        List<SeriesDto> seriesList = new ArrayList<>();
        seriesList.add(seriesDto1);
        seriesList.add(seriesDto2);

        // When Then
        when(seriesService.getAll()).thenReturn(seriesList);
        mockMvc.perform(get("/v1/series/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("none1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("none2")));
    }

    @Test
    public void testGetEmptyList() throws Exception {
        // Given
        List<SeriesDto> list = new ArrayList<>();
        when(seriesService.getAll()).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/series/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetSeriesById() throws Exception {
        // Given
        SeriesDto seriesDto = new SeriesDto(1L, "none");

        // When Then
        when(seriesService.getById(1L)).thenReturn(seriesDto);
        mockMvc.perform(get("/v1/series/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("none")));
     }

    @Test
    public void testGetSeriesThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.SERIES_ID_NOT_FOUND, "100")).when(seriesService).getById(100L);

        // When & Then
        mockMvc.perform(get("/v1/series/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetSeriesByTitle() throws Exception {
        // Given
        SeriesDto seriesDto1 = new SeriesDto(1L, "none");
        SeriesDto seriesDto2 = new SeriesDto(2L, "none II");
        List<SeriesDto> series = Arrays.asList(seriesDto1, seriesDto2);

        // When Then
        when(seriesService.getByTitle("none")).thenReturn(series);
        mockMvc.perform(get("/v1/series")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "none"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("none")))
                .andExpect(jsonPath("$[1].title", is("none II")));
    }

    @Test
    public void testCreateNewSeries() throws Exception {
        // Given
        SeriesDto seriesDto = new SeriesDto(1L, "None");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(seriesDto);

        // When & Then
        mockMvc.perform(post("/v1/series")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateSeries() throws Exception {
        // Given
        SeriesDto seriesDtoIn = new SeriesDto(1L, "none");
        SeriesDto seriesDtoOut = new SeriesDto(1L, "None");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(seriesDtoIn);

        // When Then
        when(seriesService.update(ArgumentMatchers.any(SeriesDto.class))).thenReturn(seriesDtoOut);
        mockMvc.perform(put("/v1/series")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("None")));
    }

    @Test
    public void testDeleteSeries() throws Exception {
        // Given
        doNothing().when(seriesService).delete(1L);

        // When Then
        mockMvc.perform(delete("/v1/series/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteSeriesThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.SERIES_ID_NOT_FOUND, "100")).when(seriesService).delete(100L);

        // When & Then
        mockMvc.perform(delete("/v1/series/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }
}
