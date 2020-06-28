package com.lab.labbook.mapper;

import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.dto.CurrencyRateDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyRateMapper {

    public CurrencyRateDto mapToDto(CurrencyRate currencyRate) {
        return new CurrencyRateDto(currencyRate.getId(), currencyRate.getSymbol(), currencyRate.getExchange(), currencyRate.getCommercial());
    }

    public List<CurrencyRateDto> mapToListDto(List<CurrencyRate> rates) {
        return rates.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
