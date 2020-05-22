package com.lab.labbook.validator;

import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.MaterialMapper;
import com.lab.labbook.repository.CurrencyRateRepository;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.repository.SupplierRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaterialValidatorTestSuite {

    @Autowired
    private MaterialValidator validator;

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    public void testValidateMaterial() {
        // Given
        CurrencyRate currencyRate = new CurrencyRate("AAA", new BigDecimal("2.4567"), new BigDecimal("3.4"));
        currencyRateRepository.save(currencyRate);
        Supplier supplier = new Supplier("name", "shortName", "", "", false, "");
        supplierRepository.save(supplier);
        MaterialDto materialDto = new MaterialDto(
                1L,
                "material",
                new BigDecimal("1.1"),
                new BigDecimal("25.0"),
                currencyRate.getId(),
                "USD",
                supplier.getId(),
                new HashMap<>());

        // When
        Material material = validator.validateMaterial(materialDto);

        // Then
        assertEquals("material", material.getName());
        assertEquals(currencyRate, material.getCurrencyRate());
        assertEquals(new BigDecimal("1.1"), material.getPrice());
        assertEquals(new BigDecimal("25.0"), material.getVoc());

        // Clean
        currencyRateRepository.delete(currencyRate);
        supplierRepository.delete(supplier);
    }

    @Test
    public void testValidateMaterialWithNullValues() {
        // Given
        CurrencyRate currencyRate = new CurrencyRate("AAA", new BigDecimal("2.4567"), new BigDecimal("3.4"));
        currencyRateRepository.save(currencyRate);
        Supplier supplier = new Supplier("name", "shortName", "", "", false, "");
        supplierRepository.save(supplier);
        MaterialDto materialDto = new MaterialDto(
                1L,
                "material",
                null,
                null,
                currencyRate.getId(),
                "USD",
                supplier.getId(),
                new HashMap<>());

        // When
        Material material = validator.validateMaterial(materialDto);

        // Then
        assertEquals("material", material.getName());
        assertEquals(currencyRate, material.getCurrencyRate());
        assertEquals(BigDecimal.ZERO, material.getPrice());
        assertEquals(BigDecimal.ZERO, material.getVoc());

        // Clean
        currencyRateRepository.delete(currencyRate);
        supplierRepository.delete(supplier);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testValidateName() {
        // Given

        // When Then
        validator.validateName("");
     }

     @Test(expected = ForbiddenOperationException.class)
     public void testValidateNameChange() {
        // Given

        // When Then
         validator.validateNameChange("old", "new");
     }
}
