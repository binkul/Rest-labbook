package com.lab.labbook.facade;

import com.lab.labbook.entity.Ingredient;
import com.lab.labbook.entity.Price;
import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.mapper.IngredientMapper;
import com.lab.labbook.service.IngredientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class IngredientFacadeTestSuite {

    @InjectMocks
    private IngredientFacade facade;

    @Mock
    private IngredientService service;

    @Mock
    private IngredientMapper mapper;

    @Test
    public void testFetchGetAll() {
        // Given
        IngredientDto ingredientDto = new IngredientDto(
                1L,
                1,
                new BigDecimal("3.4"),
                "none",
                1L,
                1L
        );
        List<IngredientDto> ingredientDtos = Collections.singletonList(ingredientDto);
        List<Ingredient> ingredients = new ArrayList<>();

        // When
        when(service.getAll(1L)).thenReturn(ingredients);
        when(mapper.mapToListDto(ingredients)).thenReturn(ingredientDtos);
        List<IngredientDto> result = facade.fetchGetAll(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1, result.get(0).getOrdinal());
        assertEquals(new BigDecimal("3.4"), result.get(0).getAmount());
        assertEquals("none", result.get(0).getComment());
        assertEquals(1L, result.get(0).getLabId());
        assertEquals(1L, result.get(0).getMaterialId());
    }

    @Test
    public void testFetchGetById() {
        // Given
        IngredientDto ingredientDto = new IngredientDto(
                1L,
                1,
                new BigDecimal("3.4"),
                "none",
                1L,
                1L
        );
        Ingredient ingredient = new Ingredient();

        // When
        when(service.getById(1L)).thenReturn(ingredient);
        when(mapper.mapToDto(ingredient)).thenReturn(ingredientDto);
        IngredientDto result = facade.fetchGetById(1L);

        // Then
        assertEquals(1L, result.getId());
        assertEquals(1, result.getOrdinal());
        assertEquals(new BigDecimal("3.4"), result.getAmount());
        assertEquals("none", result.getComment());
        assertEquals(1L, result.getLabId());
        assertEquals(1L, result.getMaterialId());
    }

    @Test
    public void testGetPrice() {
        // Given
        Price price = new Price(new BigDecimal("2.2"), new BigDecimal("4.45"));

        // When
        when(service.getPrice(1L)).thenReturn(price);
        Price result = facade.fetchGetPrice(1L);

        // Then
        assertEquals(new BigDecimal("2.2"), result.getNbpPrice());
        assertEquals(new BigDecimal("4.45"), result.getCommercialPrice());
    }

    @Test
    public void testFetchGetVoc() {
        // Given

        // When
        when(service.getVoc(1L)).thenReturn(new BigDecimal("2.4"));
        BigDecimal result = facade.fetchGetVoc(1L);

        // Then
        assertEquals(new BigDecimal("2.4"), result);
    }

    @Test
    public void testFetchGetSum() {
        // Given

        // When
        when(service.getAmountSum(1L)).thenReturn(new BigDecimal("100.00"));
        BigDecimal result = facade.fetchSum(1L);

        // Then
        assertEquals(new BigDecimal("100.00"), result);
    }
}
