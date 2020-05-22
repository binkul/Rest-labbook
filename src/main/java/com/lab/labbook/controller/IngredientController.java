package com.lab.labbook.controller;

import com.lab.labbook.entity.Price;
import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.entity.dto.IngredientMoveDto;
import com.lab.labbook.facade.IngredientFacade;
import com.lab.labbook.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/recipe")
public class IngredientController {

    private final IngredientFacade facade;

    @GetMapping(value = "/all/{labId}")
    public List<IngredientDto> getAll(@PathVariable Long labId) {
        return facade.fetchGetAll(labId);
    }

    @GetMapping(value = "/{id}")
    public IngredientDto getIngredient(@PathVariable Long id) {
        return facade.fetchGetById(id);
    }

    @GetMapping(value = "/price/{labId}")
    public Price getPrice(@PathVariable Long labId) {
        return facade.fetchGetPrice(labId);
    }

    @GetMapping(value = "/voc/{labId}")
    public BigDecimal getVoc(@PathVariable Long labId) {
        return facade.fetchGetVoc(labId);
    }

    @GetMapping(value = "/sum/{labId}")
    public BigDecimal getSum(@PathVariable long labId) {
        return facade.fetchSum(labId);
    }

    @PostMapping
    public void add(@RequestBody IngredientDto ingredientDto) {
        facade.fetchAdd(ingredientDto);
    }

    @PutMapping
    public void update(@RequestBody IngredientDto ingredientDto) {
        facade.fetchUpdate(ingredientDto);
    }

    @PutMapping(value = "/move")
    public void move(@RequestBody IngredientMoveDto moveDto) {
        facade.fetchMove(moveDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        facade.fetchDelete(id);
    }
}
