package com.lab.labbook.client.weather;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherConfig {

    @Value("${weather.base.url}")
    private String baseWeatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;
}
