package com.lab.labbook.entity.clp;

import com.lab.labbook.entity.Material;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClpSymbol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Material material;

    public ClpSymbol(String symbol, Material material) {
        this.symbol = symbol;
        this.material = material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(!(o instanceof ClpSymbol)) return false;
        ClpSymbol clpSymbol = (ClpSymbol) o;
        return Objects.equals(id, clpSymbol.id) &&
                Objects.equals(symbol, clpSymbol.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, symbol);
    }
}
