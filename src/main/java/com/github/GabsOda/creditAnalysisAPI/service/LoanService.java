package com.github.GabsOda.creditAnalysisAPI.service;

import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanCreateDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.response.MessageResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.entity.Loan;
import com.github.GabsOda.creditAnalysisAPI.exception.LoanException;
import com.github.GabsOda.creditAnalysisAPI.exception.LoanNotFoundException;
import com.github.GabsOda.creditAnalysisAPI.mapper.ClientMapper;
import com.github.GabsOda.creditAnalysisAPI.mapper.LoanMapper;
import com.github.GabsOda.creditAnalysisAPI.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService {

    private LoanRepository loanRepository;

    private ClientService clientService;

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    private final LoanMapper loanMapper = LoanMapper.INSTANCE;

    public MessageResponseDTO createLoan(Long id, LoanCreateDTO loanCreateDTO) throws LoanException{
        ClientResponseDTO clientResponseDTO = clientMapper.modelToRequest(clientService.verifyIfClientExists(id));

        LoanDTO converted = loanMapper.createToDto(loanCreateDTO);
        converted.setClient(clientResponseDTO);

        Loan loanToSave = loanMapper.toModel(converted);

        if (loanToSave.getQuantity() > 60) {
            throw new LoanException("Loan exceeds maximum amount of installments");
        } else {
            LocalDate now = LocalDate.now();

            if (loanToSave.getFirstInstallment().isBefore(now)) {
                throw new LoanException("Invalid loan date");
            } else if (loanToSave.getFirstInstallment().isAfter(now.plusMonths(3))) {
                throw new LoanException("The date of the first installment must be no later than 3 months after the current day");
            } else {
                Loan savedLoan = loanRepository.save(loanToSave);

                return createMessageResponse(savedLoan.getId(), "Created Loan with ID: ");
            }
        }
    }

    public List<LoanDTO> listAll() {
        List<Loan> allLoans = loanRepository.findAll();

        return allLoans.stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    public LoanDTO findByLoanId(Long id) throws LoanNotFoundException {
        Loan loan = verifyIfLoanExists(id);

        return loanMapper.toDto(loan);
    }

    public List<LoanDTO> findByClientId(Long id) throws LoanNotFoundException {
        clientService.verifyIfClientExists(id);

        return loanRepository.findByClientId(id).stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    public Loan verifyIfLoanExists(Long id) throws LoanNotFoundException {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException("Not found loan with Id: ", id)) ;
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }

}
