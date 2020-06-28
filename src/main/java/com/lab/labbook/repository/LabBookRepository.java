package com.lab.labbook.repository;

import com.lab.labbook.entity.LabBook;
import com.lab.labbook.entity.Series;
import com.lab.labbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface LabBookRepository extends JpaRepository<LabBook, Long> {

    @Modifying
    @Query(value = "UPDATE LabBook SET user = :newUser WHERE user = :oldUser")
    void updateUserId(@Param("oldUser") User oldUser, @Param("newUser") User newUser);

    @Modifying
    @Query(value = "UPDATE LabBook SET series = :newSeries WHERE series = :oldSeries")
    void updateSeriesId(@Param("oldSeries") Series oldSeries, @Param("newSeries") Series newSeries);

    @Query(value = "SELECT l from LabBook l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :searchText, '%')) ORDER BY l.id")
    List<LabBook> findAllByTitleOrderById(@Param("searchText") String searchText);

    @Query(value = "SELECT l FROM LabBook l WHERE LOWER(l.title) like LOWER(CONCAT('%', :searchText, '%')) " +
            "AND l.status NOT LIKE :status ORDER BY l.id")
    List<LabBook> findAllByTitleAndStatusNotLikeOrderById(@Param("searchText") String searchText,
                                                          @Param("status") String status);

    @Query(value = "SELECT l FROM LabBook l WHERE LOWER(l.title) like LOWER(CONCAT('%', :searchText, '%')) " +
            "AND l.user = :user AND l.status NOT LIKE :status ORDER BY l.id")
    List<LabBook> findAllByUserAndTitleAndStatusNotLikeOrderById(@Param("user") User user,
                                                                 @Param("searchText") String searchText,
                                                                 @Param("status") String status);

    boolean existsByUser(User user);
    boolean existsBySeries(Series series);

    int countByStatus(String status);
    List<LabBook> findAllByOrderById();
    List<LabBook> findByUserAndStatusIsNotOrderById(User user, String status);
}
