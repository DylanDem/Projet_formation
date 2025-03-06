package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final String NULLABLE_ID = "Non present ID";
    private static final String REGEX_PW = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$";
    private final ClientDao clientDao;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, PasswordEncoder passwordEncoder) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private void clientVerify(ClientRequestDto clientRequestDto) throws ClientException {
        if (clientRequestDto == null)
            throw new ClientException("ClientRequestDto is null");
        if (clientRequestDto.name() == null || clientRequestDto.name().isBlank())
            throw new ClientException("Client's name is absent");
        if (clientRequestDto.firstName() == null || clientRequestDto.firstName().isBlank())
            throw new ClientException("Client's first name is absent");
        if (clientRequestDto.email() == null || clientRequestDto.email().isBlank())
            throw new ClientException("Client's email is absent");
        if (clientDao.existsByEmail(clientRequestDto.email()))
            throw new ClientException("Client's email already exists");
        if (clientRequestDto.birthDate() == null)
            throw new ClientException("Client's birth date is absent");
        if (!clientRequestDto.password().matches(REGEX_PW))
            throw new ClientException("Client's password is not valid");
        if (clientRequestDto.address().town() == null)
            throw new ClientException("Client's town is absent");
        if (clientRequestDto.address().street() == null)
            throw new ClientException("Client's street is absent");
        if (clientRequestDto.address().postalCode() == null)
            throw new ClientException("Client's postal code is absent");
        if (Period.between(clientRequestDto.birthDate(), LocalDate.now()).getYears() < 18)
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


    /**
     * Retrieves a client based on their email.
     *
     * @param email The email of the client to retrieve
     * @return The ClientResponseDto object containing the client's information
     * @throws EntityNotFoundException If the client is not found
     */
    @Override
    public ClientResponseDto toFind(String email) throws EntityNotFoundException {
        Optional<Client> optClient = clientDao.findById(email);
        if (optClient.isEmpty()) throw new EntityNotFoundException("Login not found");
        Client client = optClient.get();
        return clientMapper.toClientResponseDto(client);
    }



    /**
     * Retrieves a list of all clients.
     *
     * @return A list of ClientResponseDto objects
     */
    @Override
    public List<ClientResponseDto> toFindAll() {

        return clientDao.findAll().stream()
                .map(clientMapper::toClientResponseDto)
                .toList();
    }



    /**
     * Retrieves client information based on their email and password.
     *
     * @param email The email of the client to retrieve
     * @param password The password of the client to retrieve
     * @return The ClientResponseDto object containing the client's information
     * @throws EntityNotFoundException If the credentials are invalid
     */
    public ClientResponseDto getInfos (String email, String password) {


        Optional<Client> optClient = clientDao.findByEmail(email);
        Client client = optClient.orElseThrow(() -> new EntityNotFoundException("Invalid credentials"));

        if (password != null && !password.equals(client.getPassword()))
            throw new EntityNotFoundException("Invalid credentials");


        return clientMapper.toClientResponseDto(client);


    }


    /**
     * Adds a new client based on the provided ClientRequestDto.
     *
     * @param clientRequestDto The ClientRequestDto containing the new client's information
     * @return The ClientResponseDto object containing the added client's information
     * @throws ClientException If there is an error adding the client
     */
    @Override
    public ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException {
        clientVerify(clientRequestDto);
        Client client = clientMapper.toClient(clientRequestDto);
        String password = passwordEncoder.encode(client.getPassword());
        client.setPassword(password);
        Client backedClient = clientDao.save(client);

        return clientMapper.toClientResponseDto(backedClient);

    }


    /**
     * Updates an existing client based on their email, password, and the provided ClientRequestDto.
     *
     * @param email The email of the client to update
     * @param password The password of the client to update
     * @param clientRequestDto The ClientRequestDto containing the updated client's information
     * @return The ClientResponseDto object containing the updated client's information
     * @throws ClientException If there is an error updating the client
     * @throws EntityNotFoundException If the client is not found
     */
    @Override
    public ClientResponseDto toUpdate(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        if (!clientDao.existsById(email))
            throw new EntityNotFoundException(NULLABLE_ID);

        clientVerify(clientRequestDto);

        Client client = clientMapper.toClient(clientRequestDto);
        client.setEmail(email);

        Client registrdClient = clientDao.save(client);
        return clientMapper.toClientResponseDto(registrdClient);
    }

    /**
     * Partially updates an existing client based on their email and the provided ClientRequestDto.
     *
     * @param email The email of the client to partially update
     * @param clientRequestDto The ClientRequestDto containing the updated client's information
     * @return The ClientResponseDto object containing the updated client's information
     * @throws ClientException If there is an error updating the client
     * @throws EntityNotFoundException If the client is not found
     */
    @Override
    public ClientResponseDto toPartiallyUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException {
        Optional<Client> clientOptional = clientDao.findById(email);
        if (clientOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Client existingClient = clientOptional.get();

        Client client = clientMapper.toClient(clientRequestDto);

        toReplace(client,existingClient);
        Client registrdClient = clientDao.save(existingClient);
        return clientMapper.toClientResponseDto(registrdClient);
    }


    /**
     * Deletes a client based on their email and password.
     *
     * @param email The email of the client to delete
     * @param password The password of the client to delete
     * @throws EntityNotFoundException If the client is not found
     */
    @Override
    public void delete(String email, String password) throws EntityNotFoundException {
        if (clientDao.existsById(email))
            clientDao.deleteById(email);

    }


}
