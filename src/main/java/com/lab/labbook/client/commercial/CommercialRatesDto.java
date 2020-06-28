package com.lab.labbook.client.commercial;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialRatesDto {
    @JsonProperty("EUR")
    private BigDecimal eur;
    @JsonProperty("USD")
    private BigDecimal usd;
    @JsonProperty("CHF")
    private BigDecimal chf;
    @JsonProperty("GBP")
    private BigDecimal gbp;
    @JsonProperty("RUB")
    private BigDecimal rub;
}
