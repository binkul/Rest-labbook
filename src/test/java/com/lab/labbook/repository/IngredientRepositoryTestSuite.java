package com.lab.labbook.repository;

import com.lab.labbook.entity.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientRepositoryTestSuite {

    private CurrencyRate currencyRate;
    private Supplier supplier;
    private Material material;
    private User user;
    private LabBook labBook;
    private Series series;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private CurrencyRateRepository currencyRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private LabBookRepository labBookRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Before
    public void prepareData() {
        currencyRate = new CurrencyRate("AAAA", new BigDecimal("2"), new BigDecimal("2"));
        currencyRepository.save(currencyRate);
        supplier = new Supplier("name", "shortName", "", "", false, "");
        supplierRepository.save(supplier);

        material = new Material();
        material.setName("Material");
        material.setCurrencyRate(currencyRate);
        material.setVoc(new BigDecimal("10"));
        material.setSupplier(supplier);
        material.setPrice(new BigDecimal("12.3"));
        materialRepository.save(material);

        series = new Series("series");
        seriesRepository.save(series);

        user = new User.UserBuilder()
                .name("Rob")
                .lastName("Brown")
                .login("jbr")
                .email("cos@interia.pl")
                .password("aaaa")
                .blocked(true)
                .role(Role.ADMIN.name())
                .date(LocalDateTime.now())
                .build();
        userRepository.save(user);

        labBook = new LabBook.LabBuilder()
                .title("New one")
                .description("New experiment")
                .conclusion("No influence on air pollution")
                .density(BigDecimal.valueOf(1.546))
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .status("CREATED")
                .user(user)
                .series(series)
                .build();
        labBookRepository.save(labBook);
    }

    @After
    public void cleanData() {
        materialRepository.delete(material);
        currencyRepository.delete(currencyRate);
        supplierRepository.delete(supplier);

        labBookRepository.delete(labBook);
        seriesRepository.delete(series);
        userRepository.delete(user);
    }

    @Test
    public void testFindByLabBookOrderByOrdinalAsc() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        List<Ingredient> ingredients = ingredientRepository.findByLabBookOrderByOrdinalAsc(labBook);

        // Then
        assertEquals(2, ingredients.size());
        assertEquals(1, ingredients.get(0).getOrdinal());
        assertEquals(2, ingredients.get(1).getOrdinal());
        assertEquals(new BigDecimal("25.5000"), ingredients.get(0).getAmount());
        assertEquals(new BigDecimal("30.5000"), ingredients.get(1).getAmount());
        assertEquals("none A", ingredients.get(0).getComment());
        assertEquals("none B", ingredients.get(1).getComment());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testFindAllRecipeVoc() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<BigDecimal> voc = ingredientRepository.findAllRecipeVoc(labBook);

        // Then
        assertTrue(voc.isPresent());
        assertEquals(new BigDecimal("560.000000"), voc.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testFindAllPriceBank() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<BigDecimal> voc = ingredientRepository.findAllRecipePriceBank(labBook);

        // Then
        assertTrue(voc.isPresent());
        assertEquals(new BigDecimal("1377.6000000000"), voc.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testFindAllPriceCommercial() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<BigDecimal> voc = ingredientRepository.findAllRecipePriceCommercial(labBook);

        // Then
        assertTrue(voc.isPresent());
        assertEquals(new BigDecimal("1377.6000000000"), voc.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testSumByAmount() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<BigDecimal> sum = ingredientRepository.sumByAmount(labBook);

        // Then
        assertTrue(sum.isPresent());
        assertEquals(new BigDecimal("56.0000"), sum.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testExistsByMaterial() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        boolean found = ingredientRepository.existsByMaterial(material);

        // Then
        assertTrue(found);

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testFindFirstByLabBookOrderByOrdinalDesc() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5000"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5000"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<Ingredient> found = ingredientRepository.findFirstByLabBookOrderByOrdinalDesc(labBook);

        // Then
        assertTrue(found.isPresent());
        assertEquals(ingredientB, found.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }

    @Test
    public void testFindByLabBookAndOrdinal() {
        // Given
        Ingredient ingredientA = new Ingredient(1, new BigDecimal("25.5000"), "none A", labBook, material);
        Ingredient ingredientB = new Ingredient(2, new BigDecimal("30.5000"), "none B", labBook, material);
        ingredientRepository.save(ingredientA);
        ingredientRepository.save(ingredientB);

        // When
        Optional<Ingredient> found = ingredientRepository.findByLabBookAndOrdinal(labBook, 1);

        // Then
        assertTrue(found.isPresent());
        assertEquals(ingredientA, found.get());

        // Clean
        ingredientRepository.delete(ingredientA);
        ingredientRepository.delete(ingredientB);
    }


}
