package com.github.GabsOda.creditAnalysisAPI.dto.request;

import com.github.GabsOda.creditAnalysisAPI.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private Long id;

    @NotNull
    private Double value;

    @NotEmpty
    private String firstInstallment;

    @NotNull
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @NotEmpty
    private ClientDTO client;

}
