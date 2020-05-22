package com.lab.labbook.client.weather;

import com.lab.labbook.client.nbp.BankClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class WeatherClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankClient.class);

    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    public Weatherbit getWeatherData(String city, String country) {
        try {
            Weatherbit weatherbit = restTemplate.getForObject(getWeatherUrl(city, country), Weatherbit.class);
            return Optional.ofNullable(weatherbit).orElseGet(Weatherbit::new);
        } catch (RestClientException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new Weatherbit();
        }
    }

    private URI getWeatherUrl(String city, String country) {
        return UriComponentsBuilder.fromHttpUrl(weatherConfig.getBaseWeatherUrl())
                .queryParam("city", city + "," + country)
                .queryParam("key", weatherConfig.getApiKey())
                .build().encode().toUri();
    }

}
