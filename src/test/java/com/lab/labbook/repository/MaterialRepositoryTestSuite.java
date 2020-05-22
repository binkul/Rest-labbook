package com.lab.labbook.repository;

import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.Supplier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaterialRepositoryTestSuite {

    private Supplier supplier;
    private CurrencyRate currencyRate;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CurrencyRateRepository currencyRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void prepareData() {
        currencyRate = new CurrencyRate("AAAA");
        currencyRepository.save(currencyRate);
        supplier = new Supplier("name", "shortName", "", "", false, "");
        supplierRepository.save(supplier);
    }

    @After
    public void cleanData() {
        currencyRepository.delete(currencyRate);
        supplierRepository.delete(supplier);
    }

    @Test
    public void testExistsByName() {
        // Given
        Material material = new Material();
        material.setName("Material");
        material.setCurrencyRate(currencyRate);
        material.setSupplier(supplier);
        material.setPrice(new BigDecimal("12.3"));
        materialRepository.save(material);

        // When
        boolean found = materialRepository.existsByName("Material");

        // Then
        assertTrue(found);

        // Clean
        materialRepository.delete(material);
    }

    @Test
    public void testFindAllByOrderByNameAsc() {
        // Given
        Material material = new Material();
        material.setName("B");
        material.setCurrencyRate(currencyRate);
        material.setSupplier(supplier);
        material.setPrice(new BigDecimal("12.3"));
        materialRepository.save(material);
        Material material_II = new Material();
        material_II.setName("A");
        material_II.setCurrencyRate(currencyRate);
        material_II.setSupplier(supplier);
        material_II.setPrice(new BigDecimal("10"));
        materialRepository.save(material_II);
        materialRepository.save(material);

        // When
        List<Material> materials = materialRepository.findAllByOrderByNameAsc();

        // Then
        assertEquals(2, materials.size());
        assertEquals("A", materials.get(0).getName());
        assertEquals("B", materials.get(1).getName());
        assertEquals(currencyRate, materials.get(0).getCurrencyRate());
        assertEquals(currencyRate, materials.get(1).getCurrencyRate());
        assertEquals(supplier, materials.get(0).getSupplier());
        assertEquals(supplier, materials.get(1).getSupplier());
        assertEquals(new BigDecimal("10.00"), materials.get(0).getPrice());
        assertEquals(new BigDecimal("12.30"), materials.get(1).getPrice());

        // Clean
        materialRepository.delete(material);
        materialRepository.delete(material_II);
    }

    @Test
    public void testFindAllByNameOrderByName() {
        // Given
        Material material = new Material();
        material.setName("Mouse");
        material.setCurrencyRate(currencyRate);
        material.setSupplier(supplier);
        material.setPrice(new BigDecimal("12.3"));
        materialRepository.save(material);
        Material material_II = new Material();
        material_II.setName("Computer");
        material_II.setCurrencyRate(currencyRate);
        material_II.setSupplier(supplier);
        material_II.setPrice(new BigDecimal("10"));
        materialRepository.save(material_II);
        materialRepository.save(material);

        // When
        List<Material> materials = materialRepository.findAllByNameOrderByName("mo");

        // Then
        assertEquals(1, materials.size());
        assertEquals("Mouse", materials.get(0).getName());
        assertEquals(currencyRate, materials.get(0).getCurrencyRate());
        assertEquals(supplier, materials.get(0).getSupplier());
        assertEquals(new BigDecimal("12.30"), materials.get(0).getPrice());

        // Clean
        materialRepository.delete(material);
        materialRepository.delete(material_II);
    }
}
