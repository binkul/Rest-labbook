package com.lab.labbook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(precision = 6, scale = 4)
    private BigDecimal exchange = BigDecimal.ONE;

    @Column(precision = 6, scale = 4)
    private BigDecimal commercial = BigDecimal.ONE;

    @OneToMany(mappedBy = "currencyRate")
    private List<Material> materialList = new ArrayList<>();

    public CurrencyRate(String symbol, BigDecimal exchange, BigDecimal commercial) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.commercial = commercial;
    }

    public CurrencyRate(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof CurrencyRate)) return false;
        CurrencyRate currencyRate = (CurrencyRate) o;
        return Objects.equals(id, currencyRate.id) &&
                Objects.equals(symbol, currencyRate.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol);
    }
}
