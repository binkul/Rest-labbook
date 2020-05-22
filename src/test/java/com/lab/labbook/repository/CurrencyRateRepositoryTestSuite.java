package com.lab.labbook.repository;

import com.lab.labbook.entity.CurrencyRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CurrencyRateRepositoryTestSuite {
    private Long id1;
    private Long id2;

    @Autowired
    private CurrencyRateRepository repository;

    @Before
    public void prepareDataBase() {
        CurrencyRate currencyRate1 = new CurrencyRate("AAA", new BigDecimal("2.4567"), new BigDecimal("3.4"));
        CurrencyRate currencyRate2 = new CurrencyRate("BBB", new BigDecimal("0.4500"), new BigDecimal("1.75"));
        repository.save(currencyRate1);
        repository.save(currencyRate2);
        id1 = currencyRate1.getId();
        id2 = currencyRate2.getId();
    }

    @After
    public void cleanDataBase() {
        repository.deleteById(id1);
        repository.deleteById(id2);
    }

    @Test
    public void testFindBySymbol() {
        // Given

        // When
        Optional<CurrencyRate> founded = repository.findBySymbol("BBB");

        // Then
        assertTrue(founded.isPresent());
        assertEquals( new BigDecimal("0.4500"), founded.get().getExchange());
    }

    @Test
    public void testChangeExchange() {
        // Given
        Optional<CurrencyRate> rate = repository.findById(id1);

        // When
        rate.ifPresent(i -> {
            rate.get().setExchange(new BigDecimal("2.0000"));
            repository.save(rate.get());
        });
        Optional<CurrencyRate> founded = repository.findBySymbol("AAA");

        // Then
        assertTrue(founded.isPresent());
        assertEquals( new BigDecimal("2.0000"), founded.get().getExchange());
    }

    @Test
    public void testExistBySymbol() {
        // Given

        // When
        boolean found = repository.existsBySymbol("AAA");
        boolean notFound = repository.existsBySymbol("ABA");

        // Then
        assertTrue(found);
        assertFalse(notFound);
    }
}
