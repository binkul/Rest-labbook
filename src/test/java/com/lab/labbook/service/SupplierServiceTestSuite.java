package com.lab.labbook.service;

import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.dto.SupplierDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ForbiddenOperationException;
import com.lab.labbook.mapper.SupplierMapper;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.repository.SupplierRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class SupplierServiceTestSuite {

    private SupplierDto supplierDto;
    private Supplier supplier;

    @InjectMocks
    private SupplierService service;

    @Mock
    private SupplierMapper mapper;

    @Mock
    private SupplierRepository repository;

    @Mock
    private MaterialRepository materialRepository;

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
        this.supplierDto = new SupplierDto(
                1L,
                "CGC sp. z o.o.",
                "CGC",
                "Base Street 54",
                "123456",
                false,
                "no comments");
    }

    @Test
    public void testGetAllSupplier() {
        // Given
        List<Supplier> suppliers = Collections.singletonList(supplier);
        List<SupplierDto> supplierDtoList = Collections.singletonList(supplierDto);

        // When
        when(repository.findAllByOrderByNameAsc()).thenReturn(suppliers);
        when(mapper.mapToDtoList(suppliers)).thenReturn(supplierDtoList);
        List<SupplierDto> founded = service.getAll();

        // Then
        assertEquals(1, founded.size());
        assertEquals(1L, founded.get(0).getId());
        assertEquals("CGC sp. z o.o.", founded.get(0).getName());
        assertEquals("CGC", founded.get(0).getShortName());
        assertEquals("Base Street 54", founded.get(0).getAddress());
        assertEquals("123456", founded.get(0).getPhones());
        assertFalse(founded.get(0).isProducer());
        assertEquals("no comments", founded.get(0).getComments());
    }

    @Test
    public void testGetAllSupplierEmptyList() {
        // Given
        List<Supplier> suppliers = new ArrayList<>();
        List<SupplierDto> supplierDtoList = new ArrayList<>();

        // When
        when(repository.findAllByOrderByNameAsc()).thenReturn(suppliers);
        when(mapper.mapToDtoList(suppliers)).thenReturn(supplierDtoList);
        List<SupplierDto> founded = service.getAll();

        // Then
        assertEquals(0, founded.size());
    }

    @Test
    public void testGetById() {
        // Given

        // When
        when(repository.findById(1L)).thenReturn(Optional.of(supplier));
        when(mapper.mapToDto(supplier)).thenReturn(supplierDto);
        SupplierDto founded = service.getById(1L);

        // Then
        assertEquals(1L, founded.getId());
        assertEquals("CGC sp. z o.o.", founded.getName());
        assertEquals("CGC", founded.getShortName());
        assertEquals("Base Street 54", founded.getAddress());
        assertEquals("123456", founded.getPhones());
        assertFalse(founded.isProducer());
        assertEquals("no comments", founded.getComments());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetByIdThrowException() {
        // Given

        // When Then
        when(repository.findById(1L)).thenReturn(Optional.empty());
        service.getById(1L);
    }

    @Test
    public void testGetAllByName() {
        // Given
        List<Supplier> suppliers = Collections.singletonList(supplier);
        List<SupplierDto> supplierDtoList = Collections.singletonList(supplierDto);

        // When
        when(repository.findAllByNameOrderByName("name")).thenReturn(suppliers);
        when(mapper.mapToDtoList(suppliers)).thenReturn(supplierDtoList);
        List<SupplierDto> founded = service.getByName("name");

        // Then
        assertEquals(1, founded.size());
        assertEquals(1L, founded.get(0).getId());
        assertEquals("CGC sp. z o.o.", founded.get(0).getName());
        assertEquals("CGC", founded.get(0).getShortName());
        assertEquals("Base Street 54", founded.get(0).getAddress());
        assertEquals("123456", founded.get(0).getPhones());
        assertFalse(founded.get(0).isProducer());
        assertEquals("no comments", founded.get(0).getComments());
    }

    @Test
    public void testGetAllByNameEmptyList() {
        // Given
        List<Supplier> suppliers = new ArrayList<>();
        List<SupplierDto> supplierDtoList = new ArrayList<>();

        // When
        when(repository.findAllByNameOrderByName("name")).thenReturn(suppliers);
        when(mapper.mapToDtoList(suppliers)).thenReturn(supplierDtoList);
        List<SupplierDto> founded = service.getByName("name");

        // Then
        assertEquals(0, founded.size());
    }

    @Test
    public void testUpdateSupplier() {
        // Given

        // When
        when(mapper.mapTuSupplier(supplierDto)).thenReturn(supplier);
        when(repository.save(supplier)).thenReturn(supplier);
        when(mapper.mapToDto(supplier)).thenReturn(supplierDto);
        SupplierDto updated = service.update(supplierDto);

        // Then
        assertEquals(1L, updated.getId());
        assertEquals("CGC sp. z o.o.", updated.getName());
        assertEquals("CGC", updated.getShortName());
        assertEquals("Base Street 54", updated.getAddress());
        assertEquals("123456", updated.getPhones());
        assertFalse(updated.isProducer());
        assertEquals("no comments", updated.getComments());
    }

    @Test(expected = ForbiddenOperationException.class)
    public void testDeleteSupplierThrowException() {
        // Given

        // When Then
        when(repository.findById(1L)).thenReturn(Optional.of(supplier));
        when(materialRepository.existsBySupplier(supplier)).thenReturn(true);
        service.delete(1L);
    }

}
