package com.lab.labbook.repository;

import com.lab.labbook.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllByOrderByNameAsc();

    @Query(value = "SELECT s FROM Supplier s WHERE LOWER(s.name) like LOWER(CONCAT(:searchText, '%')) ORDER BY s.name")
    List<Supplier> findAllByNameOrderByName(@Param("searchText") String searchText);
}
