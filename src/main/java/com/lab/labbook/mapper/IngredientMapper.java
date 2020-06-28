package com.lab.labbook.mapper;

import com.lab.labbook.entity.Ingredient;
import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.dto.IngredientDto;
import com.lab.labbook.service.LabBookService;
import com.lab.labbook.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class IngredientMapper {

    private final LabBookService labBookService;
    private final MaterialService materialService;

    public Ingredient mapToIngredient(IngredientDto ingredientDto) {
        LabBook labBook = labBookService.getLabBook(ingredientDto.getLabId());
        Material material = materialService.getMaterial(ingredientDto.getMaterialId());
        return new Ingredient(
                ingredientDto.getId(),
                ingredientDto.getOrdinal(),
                ingredientDto.getAmount(),
                ingredientDto.getComment(),
                labBook,
                material);
    }

    public IngredientDto mapToDto(Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getId(),
                ingredient.getOrdinal(),
                ingredient.getAmount(),
                ingredient.getComment(),
                ingredient.getLabBook().getId(),
                ingredient.getMaterial().getId());
    }

    public List<IngredientDto> mapToListDto(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
