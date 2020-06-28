package com.lab.labbook.client.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private String city_name;
    private Double pres;
    private Double wind_spd;
    private Double rh;
    private Double temp;
    private Double clouds;
    private Weather weather;
}
