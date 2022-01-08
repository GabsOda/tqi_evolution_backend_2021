package com.github.GabsOda.creditAnalysisAPI.repository;

import com.github.GabsOda.creditAnalysisAPI.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByClientId(Long id);

}
