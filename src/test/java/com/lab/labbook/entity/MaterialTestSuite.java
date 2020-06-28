package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.mapper.MaterialMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaterialTestSuite {

    private CurrencyRate currencyRate;
    private Supplier supplier;

    @Autowired
    private MaterialMapper materialMapper;

    @Before
    public void prepareData() {
        this.currencyRate = new CurrencyRate("USD");
        this.supplier = new Supplier("name", "shortName", "", "", false, "");
    }

    @Test
    public void testCreateMaterial() {
        // Given
        Material material = new Material(1L, "none", BigDecimal.ONE, BigDecimal.ONE, currencyRate, supplier);

        // When

        // Then
        assertEquals(BigDecimal.ONE, currencyRate.getExchange());
        assertEquals(0, currencyRate.getMaterialList().size());
        assertEquals(0, material.getIngredientList().size());
    }

    @Test
    public void testMapToMaterialDto() {
        // Given
        Material material = new Material(1L, "none", BigDecimal.ONE, BigDecimal.ONE, currencyRate, supplier);

        // When
        MaterialDto materialExtDto = materialMapper.mapToDto(material);

        // Then
        assertEquals(1L, materialExtDto.getId());
        assertEquals("none", materialExtDto.getName());
        assertEquals(BigDecimal.ONE, materialExtDto.getPrice());
        assertEquals(BigDecimal.ONE, materialExtDto.getVoc());
        assertNull(materialExtDto.getCurrencyId());
        assertNull(materialExtDto.getSupplierId());
        assertEquals("USD", materialExtDto.getSymbol());
    }

    @Test
    public void testMapToMaterialListDto() {
        // Given
        Material material1 = new Material(1L, "none", BigDecimal.ONE, BigDecimal.ONE, currencyRate, supplier);
        Material material2 = new Material(2L, "none1", BigDecimal.ZERO, BigDecimal.ZERO, currencyRate, supplier);
        Material material3 = new Material(3L, "none2", new BigDecimal("1.1"), new BigDecimal("2.5"), currencyRate, supplier);
        List<Material> materials = Arrays.asList(material1, material2, material3);

        // When
        List<MaterialDto> materialList = materialMapper.mapToListDto(materials);
        MaterialDto materialDto = materialList.get(2);

        // Then
        assertEquals(3, materialList.size());
        assertEquals(3L, materialDto.getId());
        assertEquals("none2", materialDto.getName());
        assertEquals(new BigDecimal("1.1"), materialDto.getPrice());
        assertEquals(new BigDecimal("2.5"), materialDto.getVoc());
        assertNull(materialDto.getCurrencyId());
        assertNull(materialDto.getSupplierId());
        assertEquals("USD", materialDto.getSymbol());
    }
 }
