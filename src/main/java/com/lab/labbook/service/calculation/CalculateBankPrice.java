package com.lab.labbook.service.calculation;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CalculateBankPrice implements Calculation {

    private final IngredientRepository repository;

    @Override
    public BigDecimal calculate(LabBook labBook) {
        return repository.findAllRecipePriceBank(labBook).orElse(BigDecimal.ZERO);
    }
}
