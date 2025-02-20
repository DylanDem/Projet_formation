package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.ConnectedUser;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private Licences licences;

    @Override
    public void saveClient(ClientRequestDto clientRequestDto){
        Client client = new Client(
                null, // ID
                clientRequestDto.name(),
                clientRequestDto.firstName(),
                clientRequestDto.address(),
                clientRequestDto.email(),
                clientRequestDto.password(),
                clientRequestDto.birthDate(),
                null, // birthDate
                clientRequestDto.licence(), //TODO : make Enum "Licences" work
                "N"
        );

        if (clientDao.existsByEmail(client.getEmail())) {
            throw new ClientException("Email is already used.");
        }
        clientDao.save(client);

        return new ClientResponseDto(
                client.getId(),
                client.getName(),
                client.getFirstName(),
                client.getAddress(),
                client.getEmail(),
                client.getBirthDate(),
                client.getRegistrationDate(),
                client.getLicence(), // TODO : make Enum "Licences" work
                client.getInactive()
        );
    }

    public ConnectedUser getClientByID(Long id) {
        return clientDao.findById(id)
                .orElseThrow(() -> new ClientException("Client not found"));
    }


}
