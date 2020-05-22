package com.lab.labbook.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LabBookDto {
    private Long id;
    private String title;
    private String description;
    private String conclusion;
    private BigDecimal density;
    private Long userId;
    private Long seriesId;
}
