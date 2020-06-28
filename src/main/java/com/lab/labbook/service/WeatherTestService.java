package com.lab.labbook.service;

import com.lab.labbook.client.weather.*;
import com.lab.labbook.entity.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WeatherTestService {

    private final WeatherClient weatherClient;

    public List<WeatherDto> getWeather() {
        List<WeatherDto> result = new ArrayList<>();

        for (Cities city : Cities.values()) {
            String name = city.name();
            Weatherbit weatherbit = weatherClient.getWeatherData(name, city.getSymbol());
            if (weatherbit.getCount() > 0) {
                Data data = weatherbit.getData().get(0);
                Weather weather = data.getWeather();
                result.add(new WeatherDto(
                        data.getCity_name(),
                        data.getPres().toString(),
                        data.getWind_spd().toString(),
                        data.getRh().toString(),
                        data.getTemp().toString(),
                        data.getClouds().toString(),
                        weather.getIcon(),
                        weather.getDescription()));
            }
        }
        return result;
    }
}
