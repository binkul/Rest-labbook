package com.lab.labbook.service;

import com.lab.labbook.client.commercial.CommercialClient;
import com.lab.labbook.client.commercial.CommercialDto;
import com.lab.labbook.client.commercial.CommercialRatesDto;
import com.lab.labbook.client.nbp.BankClient;
import com.lab.labbook.client.nbp.BankDto;
import com.lab.labbook.client.nbp.RatesDto;
import com.lab.labbook.entity.Currency;
import com.lab.labbook.entity.CurrencyRate;
import com.lab.labbook.entity.dto.CurrencyRateDto;
import com.lab.labbook.exception.EntityNotFoundException;
import com.lab.labbook.exception.ExceptionType;
import com.lab.labbook.mapper.CurrencyRateMapper;
import com.lab.labbook.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class CurrencyRateService {

    private final CurrencyRateMapper mapper;
    private final CurrencyRateRepository repository;
    private final BankClient bankClient;
    private final CommercialClient commercialClient;

    public List<CurrencyRateDto> getAll() {
        return mapper.mapToListDto(repository.findAll());
    }

    public CurrencyRateDto getById(Long id) {
        return mapper.mapToDto(getRate(id));
    }

    public CurrencyRate getRate(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionType.CURRENCY_ID_NOT_FOUND, id.toString()));
    }

    public void updateNationalCurrency() {
        CurrencyRate rate = repository.findBySymbol(Currency.PLN.name())
                .orElseGet(() -> new CurrencyRate(Currency.PLN.name()));
        rate.setExchange(BigDecimal.ONE);
        rate.setCommercial(BigDecimal.ONE);
        repository.save(rate);
    }

    public void updateCommercial() {
        CommercialDto commercialDto = commercialClient.getCommercialRates();
        CommercialRatesDto commercialRatesDto = commercialDto.getRates();

        CurrencyRate rate = repository.findBySymbol(Currency.EUR.name()).orElseGet(() -> new CurrencyRate(Currency.EUR.name()));
        rate.setCommercial(changeRate(commercialRatesDto.getEur()));
        repository.save(rate);
        rate = repository.findBySymbol(Currency.USD.name()).orElseGet(() -> new CurrencyRate(Currency.USD.name()));
        rate.setCommercial(changeRate(commercialRatesDto.getUsd()));
        repository.save(rate);
        rate = repository.findBySymbol(Currency.GBP.name()).orElseGet(() -> new CurrencyRate(Currency.GBP.name()));
        rate.setCommercial(changeRate(commercialRatesDto.getGbp()));
        repository.save(rate);
        rate = repository.findBySymbol(Currency.CHF.name()).orElseGet(() -> new CurrencyRate(Currency.CHF.name()));
        rate.setCommercial(changeRate(commercialRatesDto.getChf()));
        repository.save(rate);
        rate = repository.findBySymbol(Currency.RUB.name()).orElseGet(() -> new CurrencyRate(Currency.RUB.name()));
        rate.setCommercial(changeRate(commercialRatesDto.getRub()));
        repository.save(rate);
    }

    private BigDecimal changeRate(BigDecimal rate) {
        int scale = 4;
        return BigDecimal.ONE.divide(rate, scale, RoundingMode.CEILING);
    }

    public void updateExchange() {
        List<BankDto> bankDto = bankClient.getBankRates();
        if (bankDto.size() > 0) {
            List<RatesDto> rates = bankDto.get(0).getRates();
            Stream.of(Currency.values())
                    .map(Enum::name)
                    .forEach(symbol -> findSymbol(symbol, rates));
        }
    }

    private void findSymbol(String symbol, List<RatesDto> rates) {
        rates.stream()
                .filter(i -> i.getCode().equals(symbol))
                .findFirst()
                .ifPresent(i -> saveExchange(symbol, i.getMid()));
    }

    private void saveExchange(String symbol, BigDecimal exchange) {
        CurrencyRate rate = repository.findBySymbol(symbol).orElseGet(() -> new CurrencyRate(symbol));
        rate.setExchange(exchange);
        repository.save(rate);
    }
}
