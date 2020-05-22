package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.SupplierDto;
import com.lab.labbook.mapper.SupplierMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SupplierTestSuite {

    private Supplier supplier;

    @Autowired
    private SupplierMapper mapper;

    @Before
    public void prepareData() {
        this.supplier = new Supplier(
                1L,
                "CGC sp. z o.o.",
                "CGC",
                "Base Street 54",
                "123456",
                false,
                "no comments",
                new ArrayList<>());
    }

    @Test
    public void testCreateGetter() {
        // Given When

        // Then
        assertEquals(1L, supplier.getId());
        assertEquals("CGC sp. z o.o.", supplier.getName());
        assertEquals("CGC", supplier.getShortName());
        assertEquals("Base Street 54", supplier.getAddress());
        assertEquals("123456", supplier.getPhones());
        assertFalse(supplier.isProducer());
        assertEquals("no comments", supplier.getComments());
        assertEquals(0, supplier.getMaterials().size());
    }

    @Test
    public void testMapToDto() {
        // Given

        // When
        SupplierDto mapped = mapper.mapToDto(supplier);

        // Then
        assertEquals(1L, mapped.getId());
        assertEquals("CGC sp. z o.o.", mapped.getName());
        assertEquals("CGC", mapped.getShortName());
        assertEquals("Base Street 54", mapped.getAddress());
        assertEquals("123456", mapped.getPhones());
        assertFalse(mapped.isProducer());
        assertEquals("no comments", mapped.getComments());
    }

    @Test
    public void testMapToDtoList() {
        // Given
        List<Supplier> suppliers = Collections.singletonList(supplier);

        // When
        List<SupplierDto> mappedList = mapper.mapToDtoList(suppliers);

        // Then
        assertEquals(1, mappedList.size());
        assertEquals(1L, mappedList.get(0).getId());
        assertEquals("CGC sp. z o.o.", mappedList.get(0).getName());
        assertEquals("CGC", mappedList.get(0).getShortName());
        assertEquals("Base Street 54", mappedList.get(0).getAddress());
        assertEquals("123456", mappedList.get(0).getPhones());
        assertFalse(mappedList.get(0).isProducer());
        assertEquals("no comments", mappedList.get(0).getComments());
    }
    @Test
    public void testMapToSupplier() {
        // Given
        SupplierDto supplierDto = new SupplierDto(1L,
                "CGC sp. z o.o.",
                "CGC",
                "Base Street 54",
                "123456",
                false,
                "no comments");

        // When
        Supplier supplier = mapper.mapTuSupplier(supplierDto);

        // Then
        assertEquals(1L, supplier.getId());
        assertEquals("CGC sp. z o.o.", supplier.getName());
        assertEquals("CGC", supplier.getShortName());
        assertEquals("Base Street 54", supplier.getAddress());
        assertEquals("123456", supplier.getPhones());
        assertFalse(supplier.isProducer());
        assertEquals("no comments", supplier.getComments());
        assertEquals(0, supplier.getMaterials().size());
    }
}
