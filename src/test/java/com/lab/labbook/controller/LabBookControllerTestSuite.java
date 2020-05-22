package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.Status;
import com.lab.labbook.entity.dto.LabBookDto;
import com.lab.labbook.entity.extended.LabBookExtDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.service.LabBookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LabBookController.class)
public class LabBookControllerTestSuite {

    private LabBookExtDto labBookExtDto;

    @Before
    public void prepareLabBook() {
        labBookExtDto = new LabBookExtDto.LabExtBuilder()
                .id(1L)
                .title("test")
                .description("none")
                .conclusion("none1")
                .density(new BigDecimal("1.1"))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status(Status.CREATED.name())
                .userId(1L)
                .seriesId(2L)
                .build();
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabBookService service;

    @Test
    public void testGetAllLabBooks() throws Exception {
        // Given
        List<LabBookExtDto> labList = new ArrayList<>();
        labList.add(labBookExtDto);

        // When Then
        when(service.getAll()).thenReturn(labList);
        mockMvc.perform(get("/v1/lab/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].description", is("none")))
                .andExpect(jsonPath("$[0].conclusion", is("none1")))
                .andExpect(jsonPath("$[0].density", is(1.1)))
                .andExpect(jsonPath("$[0].status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].seriesId", is(2)));
    }

    @Test
    public void testGetEmptyList() throws Exception {
        // Given
        List<LabBookExtDto> list = new ArrayList<>();
        when(service.getAll()).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/lab/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetByTitle() throws Exception {
        // Given
        List<LabBookExtDto> labList = new ArrayList<>();
        labList.add(labBookExtDto);

        // When Then
        when(service.getByTitle("test")).thenReturn(labList);
        mockMvc.perform(get("/v1/lab")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].description", is("none")))
                .andExpect(jsonPath("$[0].conclusion", is("none1")))
                .andExpect(jsonPath("$[0].density", is(1.1)))
                .andExpect(jsonPath("$[0].status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].seriesId", is(2)));
    }

    @Test
    public void testGetByTitleEmptyList() throws Exception {
        // Given
        List<LabBookExtDto> list = new ArrayList<>();
        when(service.getByTitle("test")).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/lab")
                .contentType(MediaType.APPLICATION_JSON)
                .param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetByUserAndTitle() throws Exception {
        // Given
        List<LabBookExtDto> labList = new ArrayList<>();
        labList.add(labBookExtDto);

        // When Then
        when(service.getByUserAndTitle(1L,"test")).thenReturn(labList);
        mockMvc.perform(get("/v1/lab/user/1/title")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1")
                .param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].description", is("none")))
                .andExpect(jsonPath("$[0].conclusion", is("none1")))
                .andExpect(jsonPath("$[0].density", is(1.1)))
                .andExpect(jsonPath("$[0].status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].seriesId", is(2)));
    }

    @Test
    public void testGetByUserAndTitleEmptyList() throws Exception {
        // Given
        List<LabBookExtDto> list = new ArrayList<>();
        when(service.getByUserAndTitle(1L,"test")).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/lab/user/1/title")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1")
                .param("title", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetByUser() throws Exception {
        // Given
        List<LabBookExtDto> labList = new ArrayList<>();
        labList.add(labBookExtDto);

        // When Then
        when(service.getByUser(1L)).thenReturn(labList);
        mockMvc.perform(get("/v1/lab/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].description", is("none")))
                .andExpect(jsonPath("$[0].conclusion", is("none1")))
                .andExpect(jsonPath("$[0].density", is(1.1)))
                .andExpect(jsonPath("$[0].status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$[0].userId", is(1)))
                .andExpect(jsonPath("$[0].seriesId", is(2)));
    }

    @Test
    public void testGetByUserEmptyList() throws Exception {
        // Given
        List<LabBookExtDto> list = new ArrayList<>();
        when(service.getByUser(1L)).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/lab/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetById() throws Exception {
        // Given

        // When Then
        when(service.getById(1L)).thenReturn(labBookExtDto);
        mockMvc.perform(get("/v1/lab/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.description", is("none")))
                .andExpect(jsonPath("$.conclusion", is("none1")))
                .andExpect(jsonPath("$.density", is(1.1)))
                .andExpect(jsonPath("$.status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.seriesId", is(2)));
    }

    @Test
    public void testGetByIdThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.LAB_NOT_FOUND, "100")).when(service).getById(100L);

        // When & Then
        mockMvc.perform(get("/v1/lab/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateNewLabBook() throws Exception {
        // Given
        LabBookDto labBookDto = new LabBookDto(1L, "test", "none", "none1", new BigDecimal("1.1"), 1L, 2L);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(labBookDto);

        // When & Then
        mockMvc.perform(post("/v1/lab")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateLabBook() throws Exception {
        // Given
        Map<String, String> updates = new HashMap<>();
        updates.put("Parameter", "none");
        updates.put("Parameter2", "none");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updates);

        // When & Then
        when(service.update(updates, 1L)).thenReturn(labBookExtDto);
        mockMvc.perform(patch("/v1/lab/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.description", is("none")))
                .andExpect(jsonPath("$.conclusion", is("none1")))
                .andExpect(jsonPath("$.density", is(1.1)))
                .andExpect(jsonPath("$.status", is(Status.CREATED.name())))
                .andExpect(jsonPath("$.userId", is(1)))
                .andExpect(jsonPath("$.seriesId", is(2)));
    }

    @Test
    public void testDeleteLabBook() throws Exception {
        // Given
        doNothing().when(service).delete(1L);

        // When & Then
        mockMvc.perform(delete("/v1/lab/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }


}
