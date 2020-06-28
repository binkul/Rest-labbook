package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.CurrencyRateDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.service.CurrencyRateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyRateController.class)
public class CurrencyRateTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRateService service;

    @Test
    public void testGetAllCurrencyRates() throws Exception {
        // Given
        CurrencyRateDto rate1 = new CurrencyRateDto(1L,"USD", new BigDecimal("4.98"), new BigDecimal("1.1"));
        CurrencyRateDto rate2 = new CurrencyRateDto(2L, "EUR", new BigDecimal("4.05"), new BigDecimal("1.4"));
        List<CurrencyRateDto> rateDtos = Arrays.asList(rate1, rate2);

        // When Then
        when(service.getAll()).thenReturn(rateDtos);
        mockMvc.perform(get("/v1/currency").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].symbol", is("USD")))
                .andExpect(jsonPath("$[0].exchange", is(4.98)))
                .andExpect(jsonPath("$[0].commercial", is(1.1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].symbol", is("EUR")))
                .andExpect(jsonPath("$[1].exchange", is(4.05)))
                .andExpect(jsonPath("$[1].commercial", is(1.4)));
    }

    @Test
    public void testGetAllCurrencyRatesEmptyList() throws Exception {
        // Given
        List<CurrencyRateDto> rateDtos = new ArrayList<>();

        // When Then
        when(service.getAll()).thenReturn(rateDtos);
        mockMvc.perform(get("/v1/currency").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetCurrencyRatesById() throws Exception {
        // Given
        CurrencyRateDto rate1 = new CurrencyRateDto(1L,"USD", new BigDecimal("4.98"), new BigDecimal("1.1"));

        // When Then
        when(service.getById(1L)).thenReturn(rate1);
        mockMvc.perform(get("/v1/currency/1").contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.symbol", is("USD")))
                .andExpect(jsonPath("$.exchange", is(4.98)))
                .andExpect(jsonPath("$.commercial", is(1.1)));
    }

    @Test
    public void testGetCurrencyThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.CURRENCY_ID_NOT_FOUND, "100")).when(service).getById(100L);

        // When & Then
        mockMvc.perform(get("/v1/currency/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }
}
