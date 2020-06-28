package com.lab.labbook.entity;

import com.lab.labbook.entity.dto.CurrencyRateDto;
import com.lab.labbook.mapper.CurrencyRateMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRateTestSuite {

    @Autowired
    CurrencyRateMapper currencyRateMapper;

    @Test
    public void testMapCurrencyToDto() {
        // Given
        CurrencyRate rate = new CurrencyRate();
        rate.setId(1L);
        rate.setExchange(new BigDecimal("4.56"));
        rate.setMaterialList(new ArrayList<>());
        rate.setSymbol("USD");

        // When
        CurrencyRateDto rateDto = currencyRateMapper.mapToDto(rate);

        // Then
        assertEquals(1L, rateDto.getId());
        assertEquals(new BigDecimal("4.56"), rateDto.getExchange());
        assertEquals("USD", rateDto.getSymbol());
    }

    @Test
    public void testMatCurrencyToDtoList() {
        // Given
        CurrencyRate rate1 = new CurrencyRate();
        rate1.setId(1L);
        rate1.setExchange(new BigDecimal("4.56"));
        rate1.setMaterialList(new ArrayList<>());
        rate1.setSymbol("USD");
        CurrencyRate rate2 = new CurrencyRate();
        rate2.setId(2L);
        rate2.setExchange(new BigDecimal("3.98"));
        rate2.setMaterialList(new ArrayList<>());
        rate2.setSymbol("EUR");
        List<CurrencyRate> rates = Arrays.asList(rate1, rate2);

        // When
        List<CurrencyRateDto> rateDtos = currencyRateMapper.mapToListDto(rates);

        // Then
        assertEquals(1L, rateDtos.get(0).getId());
        assertEquals(new BigDecimal("4.56"), rateDtos.get(0).getExchange());
        assertEquals("USD", rateDtos.get(0).getSymbol());
        assertEquals(2L, rateDtos.get(1).getId());
        assertEquals(new BigDecimal("3.98"), rateDtos.get(1).getExchange());
        assertEquals("EUR", rateDtos.get(1).getSymbol());
    }
}
