package com.lab.labbook.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientValidatorTestSuite {

    @Autowired
    private IngredientValidator validator;

    @Test
    public void testValidateAmount() {
        // Given

        // When
        BigDecimal result = validator.validateAmount(new BigDecimal("1.1"));

        // Then
        assertEquals(new BigDecimal("1.1"), result);
    }

    @Test
    public void testValidateAmountNullValue() {
        // Given

        // When
        BigDecimal result = validator.validateAmount(null);

        // Then
        assertEquals(BigDecimal.ZERO, result);
    }
}
