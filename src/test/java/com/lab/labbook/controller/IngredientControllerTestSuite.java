package com.lab.labbook.controller;

import com.google.gson.Gson;
import com.lab.labbook.entity.Price;
import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.entity.dto.IngredientMoveDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.facade.IngredientFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(IngredientController.class)
public class IngredientControllerTestSuite {

    private IngredientDto ingredientDto;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientFacade facade;

    @Before
    public void prepareIngredient() {
        this.ingredientDto = new IngredientDto(
                1L,
                1,
                new BigDecimal("23.45"),
                "no comment",
                2L,
                3L
        );
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        // Given
        List<IngredientDto> ingredients = new ArrayList<>();
        ingredients.add(ingredientDto);

        // When Then
        when(facade.fetchGetAll(1L)).thenReturn(ingredients);
        mockMvc.perform(get("/v1/recipe/all/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("labId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].ordinal", is(1)))
                .andExpect(jsonPath("$[0].amount", is(23.45)))
                .andExpect(jsonPath("$[0].comment", is("no comment")))
                .andExpect(jsonPath("$[0].labId", is(2)))
                .andExpect(jsonPath("$[0].materialId", is(3)));
    }

    @Test
    public void testGetIngredientsEmptyList() throws Exception {
        // Given
        List<IngredientDto> list = new ArrayList<>();
        when(facade.fetchGetAll(1L)).thenReturn(list);

        // When & Then
        mockMvc.perform(get("/v1/recipe/all/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("labId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testGetIngredient() throws Exception {
        // Given

        // When Then
        when(facade.fetchGetById(1L)).thenReturn(ingredientDto);
        mockMvc.perform(get("/v1/recipe/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.ordinal", is(1)))
                .andExpect(jsonPath("$.amount", is(23.45)))
                .andExpect(jsonPath("$.comment", is("no comment")))
                .andExpect(jsonPath("$.labId", is(2)))
                .andExpect(jsonPath("$.materialId", is(3)));
    }

    @Test
    public void testGetIngredientThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.INGREDIENT_NOT_FOUND, "100")).when(facade).fetchGetById(100L);

        // When & Then
        mockMvc.perform(get("/v1/recipe/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPrice() throws Exception {
        // Given
        Price price = new Price(new BigDecimal("125.6"), new BigDecimal("245.7"));

        // When Then
        when(facade.fetchGetPrice(1L)).thenReturn(price);
        mockMvc.perform(get("/v1/recipe/price/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("labId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nbpPrice", is(125.6)))
                .andExpect(jsonPath("$.commercialPrice", is(245.7)));
    }

    @Test
    public void testGetVOC() throws Exception {
        // Given

        // When Then
        when(facade.fetchGetVoc(1L)).thenReturn(new BigDecimal("29.5"));
        mockMvc.perform(get("/v1/recipe/voc/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("labId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("29.5"));
    }

    @Test
    public void testGetSum() throws Exception {
        // Given

        // When Then
        when(facade.fetchSum(1L)).thenReturn(new BigDecimal("100"));
        mockMvc.perform(get("/v1/recipe/sum/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("labId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("100"));
    }

    @Test
    public void testAddNewIngredient() throws Exception {
        // Given
        IngredientDto ingredientDto = new IngredientDto(
                null,
                1,
                new BigDecimal("12.5"),
                "no comment",
                2L,
                3L
        );

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ingredientDto);

        // When & Then
        mockMvc.perform(post("/v1/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        // Given
        IngredientDto ingredientDto = new IngredientDto(
                1L,
                1,
                new BigDecimal("12.5"),
                "no comment",
                2L,
                3L
        );

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ingredientDto);

        // When & Then
        mockMvc.perform(put("/v1/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testMoveIngredient() throws Exception {
        // Given
        IngredientMoveDto ingredientMoveDto = new IngredientMoveDto(1L, "up");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ingredientMoveDto);

        // When Then
        mockMvc.perform(put("/v1/recipe/move")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        // Given
        doNothing().when(facade).fetchDelete(1L);

        // When Then
        mockMvc.perform(delete("/v1/recipe/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void testDeleteIngredientThrowException() throws Exception {
        // Given
        doThrow(new EntityNotFoundException(ExceptionType.INGREDIENT_NOT_FOUND, "100")).when(facade).fetchDelete(100L);

        // When & Then
        mockMvc.perform(delete("/v1/recipe/100")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "100"))
                .andExpect(status().isNotFound());
    }
}
