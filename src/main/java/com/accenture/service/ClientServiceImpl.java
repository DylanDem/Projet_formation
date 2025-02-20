package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.ConnectedUser;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private final ClientDao clientDao;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }

   //To Override in inferface
    private static void clientVerify(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null)
            throw new ClientException("ClientRequestDto is null");
        if (clientRequestDto.name() == null || clientRequestDto.name().isBlank())
            throw new ClientException("Client's name is absent");
        if (clientRequestDto.firstName() == null)
            throw new ClientException("Client's first name is absent");
        if (clientRequestDto.email() == null)
            throw new ClientException("Client's email is absent");
        if (clientRequestDto.birthDate() == null)
            throw new ClientException("Client's birth date is absent");
        if (clientRequestDto.password() == null)
            throw new ClientException("Client's passwoprd is absent");


    }


    //To Override in interface
    private static void toReplace(Client client, Client existingClient) {
        if (client.getName() != null)
            existingClient.setName(client.getName());
        if (client.getFirstName() != null)
            existingClient.setFirstName(client.getFirstName());
        if (client.getEmail() != null)
            existingClient.setEmail((client.getEmail()));
        if (client.getBirthDate() != null)
            existingClient.setBirthDate((client.getBirthDate()));
        if (client.getPassword() != null)
            existingClient.setPassword((client.getPassword()));
    }

    @Override
    public ClientResponseDto toFind(int id) throws EntityNotFoundException {
        Optional<ConnectedUser> optClient = clientDao.findById((long) id);
        if (optClient.isEmpty()) throw new EntityNotFoundException("Absent ID");
        ConnectedUser client = optClient.get();
        return clientMapper.toClientResponseDto(client);
    }


    @Override
    public List<ClientResponseDto> toFindAll() {
        return clientDao.findAll().stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
        // I think it should be List<ClientResponseDto> but i don't know why it doesn't wanna work
    }


    @Override
    public ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException {
        clientVerify(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        Client backedClient = clientDao.save(client);


        return clientMapper.toClientResponseDto(backedClient);

        // Same here I think it should be public ClientResponseDto but it doesn't wanna work somehow
    }


    @Override
    public void saveClient(ClientRequestDto clientRequestDto) {

    }
}
