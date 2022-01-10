package com.github.GabsOda.creditAnalysisAPI.service;

import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientLoginDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientLogoutDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.dto.response.MessageResponseDTO;
import com.github.GabsOda.creditAnalysisAPI.entity.Client;
import com.github.GabsOda.creditAnalysisAPI.exception.ClientNotFoundException;
import com.github.GabsOda.creditAnalysisAPI.exception.ClientNotLogged;
import com.github.GabsOda.creditAnalysisAPI.mapper.ClientMapper;
import com.github.GabsOda.creditAnalysisAPI.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientService {

    private ClientRepository clientRepository;

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    public MessageResponseDTO createClient(ClientDTO clientDTO) {
        Client clientToSave = clientMapper.toModel(clientDTO);
        clientToSave.setLoggedIn(false);

        Client savedClient = clientRepository.save(clientToSave);

        return createMessageResponse(savedClient.getId(), "Created Client with ID: ");
    }

    public MessageResponseDTO loginClient(ClientLoginDTO clientLoginDTO) throws ClientNotFoundException {
        List<Client> client = clientRepository.findByEmail(clientLoginDTO.getEmail());

        if(!client.isEmpty()){
            Client clientLogged = client.get(0);
            clientLogged.setLoggedIn(true);

            Client updatedClient = clientRepository.save(clientLogged);

            return createMessageResponse(updatedClient.getEmail(), "Logged client with email: ");
        } else {
            throw new ClientNotFoundException("Not found client with email: ", clientLoginDTO.getEmail());
        }

    }

    public MessageResponseDTO logoutClient(ClientLogoutDTO clientLogoutDTO) throws ClientNotFoundException, ClientNotLogged {
        List<Client> client = clientRepository.findByEmail(clientLogoutDTO.getEmail());

        if (!client.isEmpty()) {
            Client clientLoggedOut = client.get(0);

            if (verifyIfClientIsLogged(clientLoggedOut.getId())){
                clientLoggedOut.setLoggedIn(false);

                Client updatedClient = clientRepository.save(clientLoggedOut);

                return createMessageResponse(updatedClient.getEmail(), "Logged out client with email: ");
            } else {
                throw new ClientNotLogged("Not logged client with email: " + clientLoggedOut.getEmail());
            }

        } else {
            throw new ClientNotFoundException("Not found client with email: ", clientLogoutDTO.getEmail());
        }

    }

    public List<ClientResponseDTO> listAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::modelToRequest)
                .collect(Collectors.toList());
    }

    public ClientDTO findById(Long id) throws ClientNotFoundException, ClientNotLogged {
        if (verifyIfClientIsLogged(id)) {
            return clientMapper.toDto(verifyIfClientExists(id));
        } else {
            throw new ClientNotLogged("Not logged client with ID: " + id);
        }
    }

    public MessageResponseDTO updateById(Long id, ClientDTO clientDTO) throws ClientNotFoundException, ClientNotLogged {
        verifyIfClientExists(id);

        if (verifyIfClientIsLogged(id)) {
            Client clientToUpdate = clientMapper.toModel(clientDTO);
            Client updatedClient = clientRepository.save(clientToUpdate);

            return createMessageResponse(updatedClient.getId(), "Updated Client with ID: ");
        } else {
            throw new ClientNotLogged("Not logged client with ID: " + id);
        }

    }

    public MessageResponseDTO deleteById(Long id) throws ClientNotFoundException, ClientNotLogged {
        verifyIfClientExists(id);

        if (verifyIfClientIsLogged(id)) {
            clientRepository.deleteById(id);

            return createMessageResponse(id, "Deleted client with ID: ");
        } else {
            throw new ClientNotLogged("Not logged client with ID: " + id);
        }
    }

    public Client verifyIfClientExists(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Not found client with Id: ", id));
    }

    public boolean verifyIfClientIsLogged(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Not found client with Id: ", id))
                .getLoggedIn();
    }

    private MessageResponseDTO createMessageResponse(Long id, String message) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }

    private MessageResponseDTO createMessageResponse(String email, String message) {
        return MessageResponseDTO.builder()
                .message(message + email)
                .build();
    }

}
