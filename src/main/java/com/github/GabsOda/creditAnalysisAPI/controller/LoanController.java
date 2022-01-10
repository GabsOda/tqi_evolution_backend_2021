package com.github.GabsOda.creditAnalysisAPI.controller;

import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanDTO;
import com.github.GabsOda.creditAnalysisAPI.exception.LoanNotFoundException;
import com.github.GabsOda.creditAnalysisAPI.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanController {

    private LoanService loanService;

    @GetMapping
    public List<LoanDTO> listAllLoans() {
        return loanService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(loanService.findByLoanId(id));
        } catch (LoanNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
