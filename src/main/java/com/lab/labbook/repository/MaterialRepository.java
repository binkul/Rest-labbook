package com.lab.labbook.repository;

import com.lab.labbook.entity.Material;
import com.lab.labbook.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findAllByOrderByNameAsc();
    boolean existsByName(String name);
    boolean existsBySupplier(Supplier supplier);

    @Query(value = "SELECT m FROM Material m WHERE LOWER(m.name) like LOWER(CONCAT(:searchText, '%')) ORDER BY m.name")
    List<Material> findAllByNameOrderByName(@Param("searchText") String searchText);
}
