package com.tekpyrmid.moviee.repository;

import com.tekpyrmid.moviee.entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialRepository extends JpaRepository<Financial, Integer> {
}
