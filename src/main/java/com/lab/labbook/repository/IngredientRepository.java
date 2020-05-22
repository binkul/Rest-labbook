package com.lab.labbook.repository;

import com.lab.labbook.entity.Ingredient;
import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByLabBookOrderByOrdinalAsc(LabBook labBook);
    Optional<Ingredient> findFirstByLabBookOrderByOrdinalDesc(LabBook labBook);
    Optional<Ingredient> findByLabBookAndOrdinal(LabBook labBook, int ordinal);
    boolean existsByMaterial(Material material);

    @Query(value = "SELECT SUM(ing.amount) FROM Ingredient ing WHERE ing.labBook = :labBook")
    Optional<BigDecimal> sumByAmount(@Param("labBook") LabBook labBook);

    @Query(value = "SELECT SUM(ing.amount * mat.voc) as voc FROM Ingredient ing LEFT JOIN ing.material mat WHERE ing.labBook = :labBook")
    Optional<BigDecimal> findAllRecipeVoc(@Param("labBook") LabBook labBook);

    @Query(value = "SELECT SUM(mat.price * ing.amount * cur.exchange) as price " +
            "FROM Ingredient ing LEFT JOIN ing.material mat LEFT JOIN mat.currencyRate cur WHERE ing.labBook = :labBook")
    Optional<BigDecimal> findAllRecipePriceBank(@Param("labBook") LabBook labBook);

    @Query(value = "SELECT SUM(mat.price * ing.amount * cur.commercial) as price " +
            "FROM Ingredient ing LEFT JOIN ing.material mat LEFT JOIN mat.currencyRate cur WHERE ing.labBook = :labBook")
    Optional<BigDecimal> findAllRecipePriceCommercial(@Param("labBook") LabBook labBook);
}
