package com.lab.labbook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int ordinal;

    @Column(precision = 7, scale = 4, nullable = false)
    private BigDecimal amount = BigDecimal.ZERO;

    private String comment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private LabBook labBook;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Material material;

    public Ingredient(int ordinal, BigDecimal amount, String comment, LabBook labBook, Material material) {
        this.ordinal = ordinal;
        this.amount = amount;
        this.comment = comment;
        this.labBook = labBook;
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return ordinal == that.ordinal &&
                Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ordinal, amount);
    }
}
