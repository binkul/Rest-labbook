package com.lab.labbook.controller;

import com.lab.labbook.entity.dto.WeatherDto;
import com.lab.labbook.service.WeatherTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/weather")
public class WeatherTestController {

    private final WeatherTestService service;

    @GetMapping
    public List<WeatherDto> getWeather() {
        return service.getWeather();
    }
}
