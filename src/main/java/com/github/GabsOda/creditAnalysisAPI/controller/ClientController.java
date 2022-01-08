package com.github.GabsOda.creditAnalysisAPI.controller;

import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanCreateDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.LoanDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.response.MessageResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.exception.ClientNotFoundException;
import com.github.GabsOda.creditAnalysisAPI.exception.LoanException;
import com.github.GabsOda.creditAnalysisAPI.service.ClientService;
import com.github.GabsOda.creditAnalysisAPI.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {

    private ClientService clientService;

    private LoanService loanService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDTO> createClient(@RequestBody @Valid ClientDTO clientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientDTO));
    }

    @GetMapping("/list")
    public List<ClientResponseDTO> listAll() {
        return clientService.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok().body(clientService.findById(id));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateClient(@PathVariable("id") Long id, @RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok().body(clientService.updateById(id, clientDTO));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteClient(@PathVariable("id") Long id) {
        try{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clientService.deleteById(id));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/loan")
    public ResponseEntity<MessageResponseDTO> createLoan(@PathVariable("id") Long id, @RequestBody LoanCreateDTO loanCreateDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(loanService.createLoan(id, loanCreateDTO));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (LoanException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping("/{id}/loans")
    public ResponseEntity<List<LoanDTO>> findLoansById(@PathVariable("id")Long id) {
        try {
            return ResponseEntity.ok().body(loanService.findByClientId(id));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
