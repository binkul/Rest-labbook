package com.lab.labbook.repository;

import com.lab.labbook.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByOrderByTitleAsc();
    Optional<Series> findByTitle(String title);
    boolean existsByTitle(String title);

    @Query(value = "SELECT s FROM Series s WHERE LOWER(s.title) like LOWER(CONCAT(:searchText, '%')) ORDER BY s.title")
    List<Series> findAllByTitleOrderByTitle(@Param("searchText") String searchText);
}
