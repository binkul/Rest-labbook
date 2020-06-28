package com.lab.labbook.repository;

import com.lab.labbook.entity.clp.ClpSymbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClpSymbolRepository extends JpaRepository<ClpSymbol, Long> {
}
