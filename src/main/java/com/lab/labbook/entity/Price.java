package com.lab.labbook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Price {
    BigDecimal nbpPrice;
    BigDecimal commercialPrice;
}
