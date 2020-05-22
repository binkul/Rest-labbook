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
public class IngredientDto {
    private Long id;
    private int ordinal;
    private BigDecimal amount;
    private String comment;
    private Long labId;
    private Long materialId;
}
