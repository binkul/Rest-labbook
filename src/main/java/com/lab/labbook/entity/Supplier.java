package com.lab.labbook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String shortName;

    private String address;
    private String phones;
    private boolean producer;
    private String comments;

    @OneToMany(mappedBy = "supplier")
    private List<Material> materials = new ArrayList<>();

    public Supplier(String name, String shortName, String address, String phones, boolean producer, String comments) {
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.phones = phones;
        this.producer = producer;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Supplier)) return false;
        Supplier supplier = (Supplier) o;
        return producer == supplier.producer &&
                Objects.equals(id, supplier.id) &&
                Objects.equals(name, supplier.name) &&
                Objects.equals(shortName, supplier.shortName) &&
                Objects.equals(address, supplier.address) &&
                Objects.equals(phones, supplier.phones) &&
                Objects.equals(comments, supplier.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shortName, address, phones, producer, comments);
    }
}
