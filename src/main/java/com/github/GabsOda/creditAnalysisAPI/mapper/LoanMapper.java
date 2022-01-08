package com.github.GabsOda.creditAnalysisAPI.mapper;

import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanCreateDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanDTO;
import com.github.GabsOda.creditAnalysisAPI.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    @Mapping(target = "firstInstallment", source = "firstInstallment", dateFormat = "dd-MM-yyyy")
    Loan toModel(LoanDTO loanDTO);

    LoanDTO toDto(Loan loan);

    LoanDTO createToDto(LoanCreateDTO createDTO);

}
