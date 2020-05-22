package com.lab.labbook.facade;

import com.lab.labbook.entity.Price;
import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.entity.dto.IngredientMoveDto;
import com.lab.labbook.mapper.IngredientMapper;
import com.lab.labbook.service.IngredientService;
import com.lab.labbook.validator.IngredientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Component
public class IngredientFacade {

    private final IngredientService service;
    private final IngredientValidator validator;
    private final IngredientMapper mapper;

    public List<IngredientDto> fetchGetAll(Long labId) {
        return mapper.mapToListDto(service.getAll(labId));
    }

    public IngredientDto fetchGetById(Long id) {
        return mapper.mapToDto(service.getById(id));
    }

    public Price fetchGetPrice(Long labId) {
        return service.getPrice(labId);
    }

    public BigDecimal fetchGetVoc(Long labId) {
        return service.getVoc(labId);
    }

    public BigDecimal fetchSum(Long labId) {
        return service.getAmountSum(labId);
    }

    public void fetchAdd(IngredientDto ingredientDto) {
        ingredientDto.setAmount(validator.validateAmount(ingredientDto.getAmount()));
        service.add(mapper.mapToIngredient(ingredientDto));
    }

    public void fetchUpdate(IngredientDto ingredientDto) {
        ingredientDto.setAmount(validator.validateAmount(ingredientDto.getAmount()));
        service.update(mapper.mapToIngredient(ingredientDto));
    }

    public void fetchMove(IngredientMoveDto moveDto) {
        service.move(moveDto);
    }

    public void fetchDelete(Long id) {
        service.delete(id);
    }
}
