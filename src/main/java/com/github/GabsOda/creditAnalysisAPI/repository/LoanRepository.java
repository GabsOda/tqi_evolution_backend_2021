package com.github.GabsOda.creditAnalysisAPI.repository;

import com.github.GabsOda.creditAnalysisAPI.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

}
