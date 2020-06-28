package com.lab.labbook.service;

import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.Supplier;
import com.lab.labbook.entity.dto.MaterialDto;
import com.lab.labbook.mapper.MaterialMapper;
import com.lab.labbook.repository.MaterialRepository;
import com.lab.labbook.validator.MaterialValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Phaser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MaterialServiceTestSuite {

    @InjectMocks
    private MaterialService materialService;

    @Mock
    private MaterialMapper materialMapper;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private MaterialValidator materialValidator;

    @Test
    public void testGetAllMaterial() {
        // Given
        Material material1 = new Material(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        Material material2 = new Material(2L, "Resin", new BigDecimal("1.4"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        List<Material> materials = Arrays.asList(material1, material2);
        MaterialDto materialDto1 = new MaterialDto(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, 1L, "USD", 1L, new HashMap<>());
        MaterialDto materialDto2 = new MaterialDto(2L, "Resin", new BigDecimal("7.5"), BigDecimal.ZERO, 1L, "EUR", 1L, new HashMap<>());
        List<MaterialDto> materialDtoList = Arrays.asList(materialDto1, materialDto2);

        // When
        when(materialRepository.findAllByOrderByNameAsc()).thenReturn(materials);
        when(materialMapper.mapToListDto(materials)).thenReturn(materialDtoList);
        List<MaterialDto> foundMaterials = materialService.getAll();

        // Then
        assertEquals(2, foundMaterials.size());
        assertEquals(1L, foundMaterials.get(0).getId());
        assertEquals(2L, foundMaterials.get(1).getId());
        assertEquals(new BigDecimal("1.5"), foundMaterials.get(0).getPrice());
        assertEquals(new BigDecimal("7.5"), foundMaterials.get(1).getPrice());
        assertEquals("Socal", foundMaterials.get(0).getName());
        assertEquals("Resin", foundMaterials.get(1).getName());
        assertEquals("USD", foundMaterials.get(0).getSymbol());
        assertEquals("EUR", foundMaterials.get(1).getSymbol());
    }

    @Test
    public void testGetMaterialById() {
        // Given
        Material material = new Material(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        MaterialDto materialDto = new MaterialDto(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, 1L, "USD", 1L, new HashMap<>());

        // When
        when(materialRepository.findById(1L)).thenReturn(java.util.Optional.of(material));
        when(materialMapper.mapToDto(material)).thenReturn(materialDto);
        MaterialDto foundMaterial = materialService.getById(1L);

        // Then
        assertEquals(1L, foundMaterial.getId());
        assertEquals(new BigDecimal("1.5"), foundMaterial.getPrice());
        assertEquals("Socal", foundMaterial.getName());
        assertEquals("USD", foundMaterial.getSymbol());
    }

    @Test
    public void testGetMaterialByName() {
        // Given
        Material material1 = new Material(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        Material material2 = new Material(2L, "Resin", new BigDecimal("1.4"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        List<Material> materials = Arrays.asList(material1, material2);
        MaterialDto materialDto1 = new MaterialDto(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, 1L, "USD", 1L, new HashMap<>());
        MaterialDto materialDto2 = new MaterialDto(2L, "Resin", new BigDecimal("7.5"), BigDecimal.ZERO, 1L, "EUR", 1L, new HashMap<>());
        List<MaterialDto> materialExtDtoList = Arrays.asList(materialDto1, materialDto2);

        // When
        when(materialRepository.findAllByNameOrderByName("Socal")).thenReturn(materials);
        when(materialMapper.mapToListDto(materials)).thenReturn(materialExtDtoList);
        List<MaterialDto> foundMaterials = materialService.getByName("Socal");

        // Then
        assertEquals(2, foundMaterials.size());
        assertEquals(1L, foundMaterials.get(0).getId());
        assertEquals(2L, foundMaterials.get(1).getId());
        assertEquals(new BigDecimal("1.5"), foundMaterials.get(0).getPrice());
        assertEquals(new BigDecimal("7.5"), foundMaterials.get(1).getPrice());
        assertEquals("Socal", foundMaterials.get(0).getName());
        assertEquals("Resin", foundMaterials.get(1).getName());
        assertEquals("USD", foundMaterials.get(0).getSymbol());
        assertEquals("EUR", foundMaterials.get(1).getSymbol());
    }

    @Test
    public void testUpdateMaterial() {
        // Given
        Material material = new Material(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        MaterialDto materialDto = new MaterialDto(1L, "Resin", new BigDecimal("1.5"), BigDecimal.ZERO, 1L, "USD", 1L, new HashMap<>());
        MaterialDto materialExtDto = new MaterialDto(1L, "Socal", new BigDecimal("1.5"), BigDecimal.ZERO, 1L, "USD", 1L, new HashMap<>());

        // When
        when(materialValidator.validateMaterial(materialDto)).thenReturn(material);
        when(materialRepository.save(material)).thenReturn(material);
        when(materialMapper.mapToDto(material)).thenReturn(materialExtDto);
        MaterialDto foundMaterial = materialService.update(materialDto);

        // Then
        assertEquals(1L, foundMaterial.getId());
        assertEquals(new BigDecimal("1.5"), foundMaterial.getPrice());
        assertEquals("Socal", foundMaterial.getName());
        assertEquals("USD", foundMaterial.getSymbol());
    }
}
