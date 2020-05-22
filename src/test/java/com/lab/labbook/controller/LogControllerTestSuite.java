package com.lab.labbook.controller;

import com.lab.labbook.entity.Log;
import com.lab.labbook.entity.LogLevel;
import com.lab.labbook.entity.dto.CurrencyRateDto;
import com.lab.labbook.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LogController.class)
public class LogControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService service;

    @Test
    public void testGetAllLogs() throws Exception {
        // Given
        LocalDateTime date = LocalDateTime.now();
        Log logOne = new Log(1L, date, LogLevel.INFO.name(), "log 1", "Comment 1");
        Log logTwo = new Log(2L, date, LogLevel.WARN.name(), "log 2", "Comment 2");
        List<Log> logs = Arrays.asList(logOne, logTwo);

        // When Then
        when(service.getAll()).thenReturn(logs);
        mockMvc.perform(get("/v1/log").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].level", is("INFO")))
                .andExpect(jsonPath("$[0].log", is("log 1")))
                .andExpect(jsonPath("$[0].comments", is("Comment 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].level", is("WARN")))
                .andExpect(jsonPath("$[1].log", is("log 2")))
                .andExpect(jsonPath("$[1].comments", is("Comment 2")));
     }

    @Test
    public void testGetAllCurrencyRatesEmptyList() throws Exception {
        // Given
        List<Log> logs = new ArrayList<>();

        // When Then
        when(service.getAll()).thenReturn(logs);
        mockMvc.perform(get("/v1/log").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
