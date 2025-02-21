package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String NULLABLE_ID = "Non present ID";
    @Autowired
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
    }


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
            throw new ClientException("Client's password is absent");
        if (clientRequestDto.address().town() == null)
            throw new ClientException("Client's address is absent");
        if (clientRequestDto.address().street() == 0)
            throw new ClientException("Client's address is absent");
        if (clientRequestDto.address().postalCode() == null)
            throw new ClientException("Client's address is absent");
        if (LocalDate.now().minus(Period.between(clientRequestDto.birthDate(), LocalDate.now())).getYear() < 18)
            throw new ClientException("Client MUST be over 18 years old");


    }


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
        if (client.getAddress() != null)
            existingClient.setAddress((client.getAddress()));
    }

    @Override
    public ClientResponseDto toFind(String email) throws EntityNotFoundException {
        Optional<Client> optClient = clientDao.findById(email);
        if (optClient.isEmpty()) throw new EntityNotFoundException("Absent ID");
        Client client = optClient.get();
        return clientMapper.toClientResponseDto(client);
    }


    @Override
    public List<ClientResponseDto> toFindAll() {
        return clientDao.findAll().stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }


    @Override
    public ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException {
        clientVerify(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        Client backedClient = clientDao.save(client);


        return clientMapper.toClientResponseDto(backedClient);

    }

    @Override
    public void saveClient(ClientRequestDto clientRequestDto) {

    }


    @Override
    public ClientResponseDto toUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        if (!clientDao.existsById(email))
            throw new EntityNotFoundException(NULLABLE_ID);

        clientVerify(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);

        Client registrdClient = clientDao.save(client);
        return clientMapper.toClientResponseDto(registrdClient);
    }

    @Override
    public ClientResponseDto toPartiallyUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        Optional<Client> clientOptional = clientDao.findById(email);
        if (clientOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Client client = clientOptional.get();

        Client existingClient = clientMapper.toClient(clientRequestDto);

        toReplace((Client) client, (Client) existingClient);
        Client registrdClient = clientDao.save(existingClient);
        return clientMapper.toClientResponseDto(registrdClient);
    }


    @Override
    public void delete(String email) throws EntityNotFoundException {
        if (clientDao.existsById(email))
            clientDao.deleteById(email);

    }

    @Override
    public List<ClientResponseDto> search(String email, String firstName, String name, LocalDate birthDate, int street, String postalCode, String town, String inactive, List<Licences> licencesList, LocalDate registrationDate) {
        List<Client> list = null;
        if (email != null) list = clientDao.findByEmailContaining(email);
        else if (firstName != null) list = clientDao.findByFirstNameContaining(firstName);
        else if (name != null) list = clientDao.findByNameContaining(name);
        else if (birthDate != null) list = clientDao.findByBirthDate(birthDate);
        else if (street != 0) list = clientDao.findByAddress_StreetContaining(street);
        else if (postalCode != null) list = clientDao.findByAddress_PostalCodeContaining(postalCode);
        else if (town != null) list = clientDao.findByAddress_TownContaining(town);
        else if (inactive != null) list = clientDao.findByInactive(inactive);
        else if (registrationDate != null) list = clientDao.findByRegistrationDate(registrationDate);
        else if (licencesList != null && !licencesList.isEmpty()) {
            Licences permis = licencesList.get(0);
            list = clientDao.findByLicencesContaining(permis);
        }
        if (list == null) throw new ClientException("Please enter something valid !");
        return list.stream().map(clientMapper::toClientResponseDto).toList();
    }


}
