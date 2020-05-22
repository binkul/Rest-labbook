package com.lab.labbook.repository;

import com.lab.labbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
    List<User> findAllByObserver(boolean observer);

    @Query(value = "SELECT u from User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<User> findAllByName(@Param("searchText") String searchText);
}
