package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.dto.SupplierDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.service.SupplierService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SupplierController.class)
public class SupplierControllerTestSuite {

    private SupplierDto supplierDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierService service;

    @Before
    public void prepareData() {
        this.supplierDto = new SupplierDto(
                1L,
                "name",
                "short name",
                "empty",
                "1234",
                true,
                "no comment");
    }

    @Test
    public void testGetAllSuppliers() throws Exception {
        // Given
        List<SupplierDto> suppliers = new ArrayList<>();
        suppliers.add(supplierDto);

        // When Then
        when(service.getAll()).thenReturn(suppliers);
        mockMvc.perform(get("/v1/supplier/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].shortName", is("short name")))
                .andExpect(jsonPath("$[0].address", is("empty")))
                .andExpect(jsonPath("$[0].phones", is("1234")))
                .andExpect(jsonPath("$[0].producer", is(true)))
                .andExpect(jsonPath("$[0].comments", is("no comment")));
    }

    @Test
    public void testGetEmptyList() throws Exception {
        // Given
        List<SupplierDto> list = new ArrayList<>();
        when(service.getAll()).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/supplier/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetSupplierByName() throws Exception {
        // Given
        List<SupplierDto> suppliers = new ArrayList<>();
        suppliers.add(supplierDto);

        // When Then
        when(service.getByName("test")).thenReturn(suppliers);
        mockMvc.perform(get("/v1/supplier")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].shortName", is("short name")))
                .andExpect(jsonPath("$[0].address", is("empty")))
                .andExpect(jsonPath("$[0].phones", is("1234")))
                .andExpect(jsonPath("$[0].producer", is(true)))
                .andExpect(jsonPath("$[0].comments", is("no comment")));
    }

    @Test
    public void testGetSupplierByNameEmptyList() throws Exception {
        // Given
        List<SupplierDto> list = new ArrayList<>();
        when(service.getByName("test")).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/supplier")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetSupplierById() throws Exception {
        // Given

        // When Then
        when(service.getById(1L)).thenReturn(supplierDto);
        mockMvc.perform(get("/v1/supplier/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.shortName", is("short name")))
                .andExpect(jsonPath("$.address", is("empty")))
                .andExpect(jsonPath("$.phones", is("1234")))
                .andExpect(jsonPath("$.producer", is(true)))
                .andExpect(jsonPath("$.comments", is("no comment")));
    }

    @Test
    public void testGetSupplierThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.SUPPLIER_NOT_FOUND, "100")).when(service).getById(100L);

        // When & Then
        mockMvc.perform(get("/v1/supplier/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateNewSupplier() throws Exception {
        // Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(supplierDto);

        // When & Then
        mockMvc.perform(post("/v1/supplier")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateSupplier() throws Exception {
        // Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(supplierDto);

        // When & Then
        when(service.update(ArgumentMatchers.any(SupplierDto.class))).thenReturn(supplierDto);
        mockMvc.perform(put("/v1/supplier")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.shortName", is("short name")))
                .andExpect(jsonPath("$.address", is("empty")))
                .andExpect(jsonPath("$.phones", is("1234")))
                .andExpect(jsonPath("$.producer", is(true)))
                .andExpect(jsonPath("$.comments", is("no comment")));
    }

    @Test
    public void testDeleteSupplier() throws Exception {
        // Given
        doNothing().when(service).delete(1L);

        // When Then
        mockMvc.perform(delete("/v1/supplier/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteSupplierThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.SUPPLIER_NOT_FOUND, "100")).when(service).delete(100L);

        // When & Then
        mockMvc.perform(delete("/v1/supplier/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }
}
