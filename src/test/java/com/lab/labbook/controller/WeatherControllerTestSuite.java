package com.lab.labbook.controller;

import com.lab.labbook.entity.Role;
import com.lab.labbook.entity.dto.UserDto;
import com.lab.labbook.entity.dto.WeatherDto;
import com.lab.labbook.service.WeatherTestService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherTestController.class)
public class WeatherControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherTestService service;

    @Test
    public void testGetUsers() throws Exception {
        // Given
        WeatherDto weatherDto = new WeatherDto(
                "Warsaw",
                "1.23",
                "0.234",
                "100",
                "24.5",
                "98",
                "xxx",
                "nice weather");
        List<WeatherDto> weather = new ArrayList<>();
        weather.add(weatherDto);

        // When Then
        when(service.getWeather()).thenReturn(weather);
        mockMvc.perform(get("/v1/weather").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].city_name", is("Warsaw")))
                .andExpect(jsonPath("$[0].pres", is("1.23")))
                .andExpect(jsonPath("$[0].wind_spd", is("0.234")))
                .andExpect(jsonPath("$[0].rh", is("100")))
                .andExpect(jsonPath("$[0].temp", is("24.5")))
                .andExpect(jsonPath("$[0].clouds", is("98")))
                .andExpect(jsonPath("$[0].icon", is("xxx")))
                .andExpect(jsonPath("$[0].description", is("nice weather")));
    }
}
