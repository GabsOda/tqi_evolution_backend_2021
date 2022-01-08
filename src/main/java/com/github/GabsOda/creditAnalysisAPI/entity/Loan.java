package com.github.GabsOda.creditAnalysisAPI.entity;

import com.github.GabsOda.creditAnalysisAPI.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private LocalDate firstInstallment;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;
}
