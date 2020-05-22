package com.lab.labbook.service;

import com.lab.labbook.entity.Ingredient;
import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Price;
import com.lab.labbook.entity.dto.IngredientMoveDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.repository.IngredientRepository;
import com.lab.labbook.service.calculation.CalculateBankPrice;
import com.lab.labbook.service.calculation.CalculateCommercialPrice;
import com.lab.labbook.service.calculation.CalculateVoc;
import com.lab.labbook.service.calculation.Calculation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final LabBookService labBookService;

    public List<Ingredient> getAll(Long labId) {
        LabBook labBook = labBookService.getLabBook(labId);
        return ingredientRepository.findByLabBookOrderByOrdinalAsc(labBook);
    }

    public Ingredient getById(Long id) {
        return getIngredient(id);
    }

    public Price getPrice(Long labId) {
        LabBook labBook = labBookService.getLabBook(labId);
        Calculation calculator = new CalculateBankPrice(ingredientRepository);
        BigDecimal bankPrice = calculator.calculate(labBook);
        calculator = new CalculateCommercialPrice(ingredientRepository);
        BigDecimal commercialPrice = calculator.calculate(labBook);

        return new Price(calculatePricePerKg(bankPrice), calculatePricePerKg(commercialPrice));
    }

    public BigDecimal getVoc(Long labId) {
        LabBook labBook = labBookService.getLabBook(labId);
        Calculation calculator = new CalculateVoc(ingredientRepository);
        BigDecimal voc = calculator.calculate(labBook);
        BigDecimal density = labBook.getDensity();
        //voc = sum(amount*voc)/100 * density * 10
        voc = voc.multiply(density);
        voc = voc.divide(new BigDecimal("10"), 2, RoundingMode.DOWN);
        return voc;
    }

    public BigDecimal getAmountSum(Long labId) {
        LabBook labBook = labBookService.getLabBook(labId);
        return ingredientRepository.sumByAmount(labBook).orElse(BigDecimal.ZERO);
    }

    public void add(Ingredient ingredient) {
        int maxOrdinal = getMaxOrdinal(ingredient.getLabBook()) + 1;
        ingredient.setOrdinal(maxOrdinal);

        ingredientRepository.save(ingredient);
    }

    public void move(IngredientMoveDto moveDto) {
        if (moveDto.getMove().equals("up")) {
            moveUp(moveDto.getId());
        } else {
            moveDown(moveDto.getId());
        }
    }

    public void update(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    public void delete(Long id) {
        Ingredient ingredient = getIngredient(id);
        LabBook labBook = ingredient.getLabBook();
        ingredientRepository.deleteById(id);
        refactorOrdinal(labBook);
    }

    private Ingredient getIngredient(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.INGREDIENT_NOT_FOUND, id.toString()));
    }

    private void moveUp(Long id) {
        Ingredient ingredient = getIngredient(id);
        if (ingredient.getOrdinal() >= 2) {
            int newOrdinal = ingredient.getOrdinal() - 1;
            exchangeIngredients(ingredient, newOrdinal);
        }
    }

    private void moveDown(Long id) {
        Ingredient ingredient = getIngredient(id);
        int maxOrdinal = getMaxOrdinal(ingredient.getLabBook());
        if (ingredient.getOrdinal() < maxOrdinal) {
            int newOrdinal = ingredient.getOrdinal() + 1;
            exchangeIngredients(ingredient, newOrdinal);
        }
    }

    private Integer getMaxOrdinal(LabBook labBook) {
        Optional<Ingredient> ingredient = ingredientRepository.findFirstByLabBookOrderByOrdinalDesc(labBook);
        return ingredient.map(Ingredient::getOrdinal).orElse(0);
    }

    private void exchangeIngredients(Ingredient ingredient, int newOrdinal) {
        int oldOrdinal = ingredient.getOrdinal();
        Optional<Ingredient> changeIngredient = ingredientRepository.findByLabBookAndOrdinal(ingredient.getLabBook(), newOrdinal);
        changeIngredient.ifPresent(ing -> {
            ing.setOrdinal(oldOrdinal);
            ingredient.setOrdinal(newOrdinal);
            ingredientRepository.save(ingredient);
            ingredientRepository.save(ing);
        });
    }

    private void refactorOrdinal(LabBook labBook) {
        List<Ingredient> ingredients = ingredientRepository.findByLabBookOrderByOrdinalAsc(labBook);
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            ingredient.setOrdinal(i + 1);
            ingredientRepository.save(ingredient);
        }
    }

    private BigDecimal calculatePricePerKg(BigDecimal price) {
        if (price == null) return BigDecimal.ZERO;
        int scale = 2;
        // result = price/100
        return price.divide(new BigDecimal("100"), scale, RoundingMode.CEILING);
    }
}
