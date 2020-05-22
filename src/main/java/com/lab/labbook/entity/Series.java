package com.lab.labbook.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "series")
    List<LabBook> labBooks = new ArrayList<>();

    public Series(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Series)) return false;
        Series series = (Series) o;
        return Objects.equals(id, series.id) &&
                Objects.equals(title, series.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
