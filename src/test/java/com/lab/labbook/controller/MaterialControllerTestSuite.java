package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.clp.GhsDto;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.service.MaterialService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MaterialController.class)
public class MaterialControllerTestSuite {

    private MaterialDto materialDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MaterialService service;

    @Before
    public void prepareMaterial() {
        this.materialDto = new MaterialDto(
                1L,
                "none1",
                new BigDecimal("1.1"),
                new BigDecimal("2.2"),
                1L,
                "USD",
                1L,
                new HashMap<>());
    }

    @Test
    public void testGetAllMaterials() throws Exception {
        // Given
        List<MaterialDto> materialList = new ArrayList<>();
        materialList.add(materialDto);

        // When Then
        when(service.getAll()).thenReturn(materialList);
        mockMvc.perform(get("/v1/material/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("none1")))
                .andExpect(jsonPath("$[0].price", is(1.1)))
                .andExpect(jsonPath("$[0].voc", is(2.2)))
                .andExpect(jsonPath("$[0].currencyId", is(1)))
                .andExpect(jsonPath("$[0].symbol", is("USD")));
    }


    @Test
    public void testGetEmptyList() throws Exception {
        // Given
        List<MaterialDto> list = new ArrayList<>();
        when(service.getAll()).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/material/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetMaterialByName() throws Exception {
        // Given
        List<MaterialDto> materialList = new ArrayList<>();
        materialList.add(materialDto);

        // When Then
        when(service.getByName("test")).thenReturn(materialList);
        mockMvc.perform(get("/v1/material")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("none1")))
                .andExpect(jsonPath("$[0].price", is(1.1)))
                .andExpect(jsonPath("$[0].voc", is(2.2)))
                .andExpect(jsonPath("$[0].currencyId", is(1)))
                .andExpect(jsonPath("$[0].symbol", is("USD")));
    }

    @Test
    public void testGetMaterialByNameEmptyList() throws Exception {
        // Given
        List<MaterialDto> list = new ArrayList<>();
        when(service.getByName("test")).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/material")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testUpdatesGhs() throws Exception {
        // Given
        GhsDto ghsDto = new GhsDto("GHS01", true);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(ghsDto);

        // When Then
        mockMvc.perform(patch("/v1/material/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1")
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testGetMaterialById() throws Exception {
        // Given

        // When Then
        when(service.getById(1L)).thenReturn(materialDto);
        mockMvc.perform(get("/v1/material/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("none1")))
                .andExpect(jsonPath("$.price", is(1.1)))
                .andExpect(jsonPath("$.voc", is(2.2)))
                .andExpect(jsonPath("$.currencyId", is(1)))
                .andExpect(jsonPath("$.symbol", is("USD")));
    }

    @Test
    public void testGetMaterialThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.MATERIAL_NOT_FOUND, "100")).when(service).getById(100L);

        // When & Then
        mockMvc.perform(get("/v1/material/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateNewMaterial() throws Exception {
        // Given
        MaterialDto materialDto = new MaterialDto(
                1L,
                "none1",
                new BigDecimal("1.1"),
                new BigDecimal("2.2"),
                1L,
                "USD",
                1L,
                new HashMap<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(materialDto);

        // When & Then
        mockMvc.perform(post("/v1/material")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateMaterial() throws Exception {
        // Given
        MaterialDto materialDto = new MaterialDto(
                1L,
                "none1",
                new BigDecimal("1.1"),
                new BigDecimal("2.2"),
                1L,
                "USD",
                1L,
                new HashMap<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(materialDto);

        // When & Then
        when(service.update(ArgumentMatchers.any(MaterialDto.class))).thenReturn(materialDto);
        mockMvc.perform(put("/v1/material")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("none1")))
                .andExpect(jsonPath("$.price", is(1.1)))
                .andExpect(jsonPath("$.voc", is(2.2)))
                .andExpect(jsonPath("$.currencyId", is(1)))
                .andExpect(jsonPath("$.symbol", is("USD")));
    }

    @Test
    public void testDeleteMaterial() throws Exception {
        // Given
        doNothing().when(service).delete(1L);

        // When Then
        mockMvc.perform(delete("/v1/material/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteMaterialThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.MATERIAL_NOT_FOUND, "100")).when(service).delete(100L);

        // When & Then
        mockMvc.perform(delete("/v1/material/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }
}
