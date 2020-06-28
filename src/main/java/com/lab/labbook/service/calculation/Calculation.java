package com.lab.labbook.service.calculation;

import com.lab.labbook.entity.LabBook;

import java.math.BigDecimal;

public interface Calculation {
    BigDecimal calculate(LabBook labBook);
}
