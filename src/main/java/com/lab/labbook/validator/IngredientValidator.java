package com.lab.labbook.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class IngredientValidator {

    public BigDecimal validateAmount(BigDecimal amount) {
        BigDecimal result = amount;
        if (amount == null) {
            result = BigDecimal.ZERO;
        }
        return result;
    }
}
