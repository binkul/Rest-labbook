package com.lab.labbook.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private String city_name;
    private String pres;
    private String wind_spd;
    private String rh;
    private String temp;
    private String clouds;
    private String icon;
    private String description;
}
