package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.mapper.IngredientMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientTestSuite {

    @Autowired
    IngredientMapper mapper;

    @Test
    public void createNewIngredient() {
        // Given
        LabBook labBook = new LabBook();
        labBook.setId(1L);
        Material material = new Material();
        material.setId(1L);
        Ingredient ingredient = new Ingredient( 1L, 1, new BigDecimal("12.5"), "none", labBook, material);

        // When Then
        assertEquals(1L, ingredient.getId());
        assertEquals(1, ingredient.getOrdinal());
        assertEquals(new BigDecimal("12.5"), ingredient.getAmount());
        assertEquals("none", ingredient.getComment());
        assertEquals(labBook, ingredient.getLabBook());
        assertEquals(material, ingredient.getMaterial());
    }

    @Test
    public void mapToIngredientExtDto() {
        // Given
        LabBook labBook = new LabBook();
        labBook.setId(1L);
        Material material = new Material();
        material.setId(1L);
        Ingredient ingredient = new Ingredient(1L,1, new BigDecimal("12.5"), "none", labBook, material);

        // When
        IngredientDto ingredientDto = mapper.mapToDto(ingredient);

        // Then
        assertEquals(1L, ingredientDto.getId());
        assertEquals(1, ingredientDto.getOrdinal());
        assertEquals(new BigDecimal("12.5"), ingredientDto.getAmount());
        assertEquals("none", ingredientDto.getComment());
        assertEquals(1L, ingredientDto.getLabId());
        assertEquals(1L, ingredientDto.getMaterialId());
    }

    @Test
    public void mapToIngredientExtDtoList() {
        // Given
        LabBook labBook = new LabBook();
        labBook.setId(1L);
        Material material = new Material();
        material.setId(1L);
        Ingredient ingredient1 = new Ingredient(1L,1, new BigDecimal("12.5"), "none", labBook, material);
        Ingredient ingredient2 = new Ingredient(2L,2, new BigDecimal("70.9"), "none none", labBook, material);
        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);

        // When
        List<IngredientDto> ingredientDtoList = mapper.mapToListDto(ingredients);

        // Then
        assertEquals(1L, ingredientDtoList.get(0).getId());
        assertEquals(1, ingredientDtoList.get(0).getOrdinal());
        assertEquals(new BigDecimal("12.5"), ingredientDtoList.get(0).getAmount());
        assertEquals("none", ingredientDtoList.get(0).getComment());
        assertEquals(1L, ingredientDtoList.get(0).getLabId());
        assertEquals(1L, ingredientDtoList.get(0).getMaterialId());
        assertEquals(2L, ingredientDtoList.get(1).getId());
        assertEquals(2, ingredientDtoList.get(1).getOrdinal());
        assertEquals(new BigDecimal("70.9"), ingredientDtoList.get(1).getAmount());
        assertEquals("none none", ingredientDtoList.get(1).getComment());
        assertEquals(1L, ingredientDtoList.get(1).getLabId());
        assertEquals(1L, ingredientDtoList.get(1).getMaterialId());
    }

}
