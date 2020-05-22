package com.lab.labbook.service;

import com.lab.labbook.entity.*;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.repository.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class IngredientServiceTestSuite {

    private LabBook labBook;

    @InjectMocks
    private IngredientService service;

    @Mock
    private IngredientRepository repository;

    @Mock
    private LabBookService labBookService;

    @Before
    public void prepareLabBook() {
        Series series = new Series(1l, "none", new ArrayList<>());
        User user = new User.UserBuilder()
                .id(1L)
                .name("Jacek")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        this.labBook = new LabBook.LabBuilder()
                .id(1L)
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.55))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("CREATED")
                .user(user)
                .series(series)
                .build();
    }

    @Test
    public void testGetAllIngredientFromRecipe() {
        // Given
        Material material = new Material(2L, "Resin", new BigDecimal("1.4"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        Ingredient ingredient1 = new Ingredient(1L, 1, new BigDecimal("2.2"), "empty", labBook, material);
        Ingredient ingredient2 = new Ingredient(2L, 2, new BigDecimal("24.6"), "something", labBook, material);
        List<Ingredient> ingredients = Arrays.asList(ingredient1, ingredient2);

        // When
        when(labBookService.getLabBook(1L)).thenReturn(labBook);
        when(repository.findByLabBookOrderByOrdinalAsc(labBook)).thenReturn(ingredients);
        List<Ingredient> found = service.getAll(1L);

        // Then
        assertEquals(2, found.size());
        assertEquals(1L, found.get(0).getId());
        assertEquals(2L, found.get(1).getId());
        assertEquals(1, found.get(0).getOrdinal());
        assertEquals(2, found.get(1).getOrdinal());
        assertEquals(new BigDecimal("2.2"), found.get(0).getAmount());
        assertEquals(new BigDecimal("24.6"), found.get(1).getAmount());
        assertEquals("empty", found.get(0).getComment());
        assertEquals("something", found.get(1).getComment());
        assertEquals(material, found.get(0).getMaterial());
        assertEquals(material, found.get(1).getMaterial());
        assertEquals(labBook, found.get(0).getLabBook());
        assertEquals(labBook, found.get(1).getLabBook());
    }

    @Test
    public void testGetIngredientById() {
        // Given
        Material material = new Material(2L, "Resin", new BigDecimal("1.4"), BigDecimal.ZERO, new CurrencyRate(), new Supplier());
        Ingredient ingredient = new Ingredient(1L, 1, new BigDecimal("2.2"), "empty", labBook, material);

        // When
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(ingredient));
        Ingredient found = service.getById(1L);

        // Then
        assertEquals(1L, found.getId());
        assertEquals(1, found.getOrdinal());
        assertEquals(new BigDecimal("2.2"), found.getAmount());
        assertEquals("empty", found.getComment());
        assertEquals(material, found.getMaterial());
        assertEquals(labBook, found.getLabBook());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGetIngredientByIdThrowException() {
        // Given

        // When
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());
        Ingredient found = service.getById(1L);

        // Then
    }

    @Test
    public void testGetPriceCalculation() {
        // Given
        Optional<BigDecimal> priceNbp = Optional.of(new BigDecimal("25"));
        Optional<BigDecimal> priceComm = Optional.of(new BigDecimal("15"));

        // When
        when(labBookService.getLabBook(1L)).thenReturn(labBook);
        when(repository.findAllRecipePriceBank(labBook)).thenReturn(priceNbp);
        when(repository.findAllRecipePriceCommercial(labBook)).thenReturn(priceComm);
        Price found = service.getPrice(1L);

        // Then
        assertNotNull(found);
        assertEquals(new BigDecimal("0.25"), found.getNbpPrice());
        assertEquals(new BigDecimal("0.15"), found.getCommercialPrice());
    }

    @Test
    public void getVocCalculationReturn() {
        // Given
        Optional<BigDecimal> voc = Optional.of(new BigDecimal("25.00"));

        // When
        when(labBookService.getLabBook(1L)).thenReturn(labBook);
        when(repository.findAllRecipeVoc(labBook)).thenReturn(voc);
        BigDecimal found = service.getVoc(1L);

        // Then
        assertEquals(new BigDecimal("3.87"), found);
    }

    @Test
    public void testAmountSum() {
        // Given

        // When
        when(labBookService.getLabBook(1L)).thenReturn(labBook);
        when(repository.sumByAmount(labBook)).thenReturn(Optional.of(new BigDecimal("100.00")));
        BigDecimal sum = service.getAmountSum(1L);

        // Then
        assertEquals(new BigDecimal("100.00"), sum);
    }
}
