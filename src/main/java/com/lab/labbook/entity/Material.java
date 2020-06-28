package com.lab.labbook.entity;

import com.lab.labbook.entity.clp.ClpSymbol;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(precision = 5, scale = 2)
    private BigDecimal voc = BigDecimal.ZERO;

    private LocalDate updateDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    private CurrencyRate currencyRate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "material")
    private List<Ingredient> ingredientList = new ArrayList<>();

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<ClpSymbol> clpSymbols = new ArrayList<>();

    public Material(Long id, String name, BigDecimal price, BigDecimal voc, CurrencyRate currencyRate, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.voc = voc;
        this.currencyRate = currencyRate;
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(id, material.id) &&
                Objects.equals(name, material.name) &&
                Objects.equals(price, material.price) &&
                Objects.equals(voc, material.voc) &&
                Objects.equals(updateDate, material.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, voc, updateDate);
    }
}
