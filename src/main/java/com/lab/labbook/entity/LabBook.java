package com.lab.labbook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class LabBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 4096)
    private String description;

    @Column(length = 4096)
    private String conclusion;

    @Column(precision = 6, scale = 4)
    private BigDecimal density = BigDecimal.ONE;

    private LocalDateTime creationDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
    private String status = Status.CREATED.name();

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Series series;

    @OneToMany(mappedBy = "labBook")
    private List<Ingredient> ingredient = new ArrayList<>();

    public static class LabBuilder {
        private Long id;
        private String title;
        private String description;
        private String conclusion;
        private BigDecimal density = BigDecimal.ONE;
        private LocalDateTime creationDate = LocalDateTime.now();
        private LocalDateTime updateDate = LocalDateTime.now();
        private String status = Status.CREATED.name();
        private User user;
        private Series series;
        private List<Ingredient> ingredient = new ArrayList<>();

        public LabBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public LabBuilder title(String title) {
            this.title = title;
            return this;
        }

        public LabBuilder description(String description) {
            this.description = description;
            return this;
        }

        public LabBuilder conclusion(String conclusion) {
            this.conclusion = conclusion;
            return this;
        }

        public LabBuilder density(BigDecimal density) {
            this.density = density;
            return this;
        }

        public LabBuilder creationDate(LocalDateTime creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public LabBuilder updateDate(LocalDateTime updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public LabBuilder status(String status) {
            this.status = status;
            return this;
        }

        public LabBuilder user(User user) {
            this.user = user;
            return this;
        }

        public LabBuilder series(Series series) {
            this.series = series;
            return this;
        }

        public LabBuilder addToRecipe(Ingredient position) {
            this.ingredient.add(position);
            return this;
        }

        public LabBook build() {
            return new LabBook(id, title, description, conclusion, density, creationDate, updateDate, status, user, series, ingredient);
        }
    }

    private LabBook(Long id, String title, String description, String conclusion, BigDecimal density, LocalDateTime creationDate, LocalDateTime updateDate, String status, User user, Series series, List<Ingredient> ingredient) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.conclusion = conclusion;
        this.density = density;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.status = status;
        this.user = user;
        this.series = series;
        this.ingredient = ingredient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof LabBook)) return false;
        LabBook labBook = (LabBook) o;
        return Objects.equals(id, labBook.id) &&
                Objects.equals(title, labBook.title) &&
                Objects.equals(description, labBook.description) &&
                Objects.equals(conclusion, labBook.conclusion) &&
                Objects.equals(density, labBook.density) &&
                Objects.equals(status, labBook.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, conclusion, density, status);
    }
}
