package com.lab.labbook.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CurrencyRateDto {
    private Long id;
    private String symbol;
    private BigDecimal exchange;
    private BigDecimal commercial;
}
