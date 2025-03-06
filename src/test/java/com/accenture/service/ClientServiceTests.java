package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Address;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.AddressDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTests {

    @Mock
    ClientDao daoMock;

    @Mock
    ClientMapper mapperMock;

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    PasswordEncoder passwordEncoder;

    private static Client createClientTiana() {
        Client c = new Client();
        c.setName("Tessier");
        c.setFirstName("Tatiana");
        c.setEmail("tessiert@gmail.com");
        c.setPassword("Tttessier324$");
        c.setAddress(new Address("rue", "postalCode", "St-Seb"));
        c.setBirthDate(LocalDate.of(1999, 4, 30));
        c.setRegistrationDate(LocalDate.now());
        c.setLicencesList(List.of(Licences.A1));
        return c;

    }

    private static Client createClientWallace() {
        Client c = new Client();
        c.setName("Novotny");
        c.setFirstName("Wallace");
        c.setEmail("novwallace@gmail.com");
        c.setPassword("Wwwallace434%");
        c.setAddress(new Address("Feur", "fure", "poils"));
        c.setBirthDate(LocalDate.of(14, 12, 31));
        c.setRegistrationDate(LocalDate.now());
        c.setLicencesList(List.of());
        return c;
    }

    private static ClientResponseDto createClientResponseDtoTiana() {
        return new ClientResponseDto(
                "Tessier", "Tatiana", new AddressDto("street", "postalCode", "streettown"),
                "tessiertiana@gmail.com", LocalDate.of(1999, 4, 30), LocalDate.now(), List.of(Licences.B1), false);
    }

    private static ClientResponseDto createClientResponseDtoPaulin() {
        return new ClientResponseDto(
                "Novotny", "Paulin", new AddressDto("street2", "postalCode2", "streetFeur"),
                "Novotnypaulin@gmail.com", LocalDate.of(1987, 11, 2), LocalDate.now(), List.of(Licences.B), true);

    }

    private static ClientRequestDto createClientRequestDtoPaulin() {
        return new ClientRequestDto(
                "Novotny", "Paulin", new AddressDto("street2", "postalCode2", "streetFeur"),
                "Novotnypaulin@gmail.com", "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2), List.of(Licences.B));

    }





    @Test
    void testFindAll() {
        Client clientWallace = createClientWallace();
        Client clientTiana = createClientTiana();
        List<Client> clients = List.of(clientWallace, clientTiana);

        ClientResponseDto clientResponseDtoTiana = createClientResponseDtoTiana();
        ClientResponseDto clientResponseDtoPaulin = createClientResponseDtoPaulin();

        List<ClientResponseDto> dtos = List.of(clientResponseDtoPaulin, clientResponseDtoTiana );


        Mockito.when(daoMock.findAll()).thenReturn(clients);
        Mockito.when(mapperMock.toClientResponseDto(clientTiana)).thenReturn(clientResponseDtoTiana);
        Mockito.when(mapperMock.toClientResponseDto(clientWallace)).thenReturn(clientResponseDtoPaulin);

        assertEquals(dtos, clientService.toFindAll());
    }

    @DisplayName("""
            find(email) throws an exception whenever the client is not registered in the database.
            """)
    @Test
    void testDoesntExist() {
        Mockito.when(daoMock.findByEmail("Novotnypaulin@gmail.com")).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> clientService.getInfos("Novotnypaulin@gmail.com", "Wallace"));
        assertEquals("Invalid credentials", ex.getMessage());
    }


    @DisplayName("""
            find(email) throws a clientResponseDto whenever the client IS registered in the database)
            """)
    @Test
    void testDoesExist() {
        Client c = createClientWallace();
        Optional<Client> optClient = Optional.of(c);
        Mockito.when(daoMock.findById("Novotnypaulin@gmail.com")).thenReturn(optClient);
        ClientResponseDto dto = createClientResponseDtoPaulin();
        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);

        ClientResponseDto result = clientService.toFind("Novotnypaulin@gmail.com");
        assertSame(dto, result);
    }

    @DisplayName("""
            Test if null is thrown in toAdd
            """)
    @Test
    void toAddNull(){
        ClientException ex = assertThrows(ClientException.class, () ->clientService.toAdd(null));
        assertEquals("ClientRequestDto is null", ex.getMessage());
    }

    @DisplayName("Test if name is null then exception thrown")
    @Test
    void nameIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                null,
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
      ClientException clientException =  assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
      assertEquals("Client's name is absent", clientException.getMessage());


    }

    @DisplayName("Test if name is blank then exception thrown")
    @Test
    void nameIsBlank() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "\t      ",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's name is absent", clientException.getMessage());

    }

    @DisplayName("Test if first name is null then exception thrown")
    @Test
    void firstNameIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                null,
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's first name is absent", clientException.getMessage());

    }

    @DisplayName("Test if first name is blank then exception thrown")
    @Test
    void firstNameIsBlank() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "\t     ",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's first name is absent", clientException.getMessage());

    }

    @DisplayName("Test if email is null then exception thrown")
    @Test
    void emailIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                null,
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's email is absent", clientException.getMessage());

    }

    @DisplayName("Test if email is duplicated then exception thrown")
    @Test
    void emailIsDuplicated() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());

        Mockito.when(daoMock.existsByEmail("wallacepaulin@gmail.com")).thenReturn(true);
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's email already exists", clientException.getMessage());

    }

    @DisplayName("Test if email is blank then exception thrown")
    @Test
    void emailIsBlank() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "\t      ",
                "MIAOUMIAOUx01$",
                LocalDate.of(1987, 11, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's email is absent", clientException.getMessage());

    }

    @DisplayName("Test if birth date is null then exception thrown")
    @Test
    void birthDateIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                null,
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's birth date is absent", clientException.getMessage());

    }

    @DisplayName("Test if birth date doesn't require the age requirements, then exception thrown")
    @Test
    void birthDateIsYoungerThan18() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(2010, 2, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client MUST be over 18 years old", clientException.getMessage());

    }

    @DisplayName("Test if password doesn't fill the regex requirements, then exception thrown")
    @Test
    void passwordIsInvalid() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "miaou",
                LocalDate.of(2000, 2, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's password is not valid", clientException.getMessage());

    }

    @DisplayName("Test if address's town is null, then exception thrown")
    @Test
    void addressTownIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", "44300", null),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(2000, 2, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's town is absent", clientException.getMessage());

    }

    @DisplayName("Test if address's street is null, then exception thrown")
    @Test
    void addressStreetIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto(null, "44300", "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(2000, 2, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's street is absent", clientException.getMessage());

    }

    @DisplayName("Test if address's postalCode is null, then exception thrown")
    @Test
    void addressPostalCodeIsNull() {

        ClientRequestDto clientRequestDto = new ClientRequestDto(
                "Wallace",
                "Paulin",
                new AddressDto("48", null, "Nantes"),
                "wallacepaulin@gmail.com",
                "MIAOUMIAOUx01$",
                LocalDate.of(2000, 2, 2),
                new ArrayList<>());
        ClientException clientException = assertThrows(ClientException.class, () -> clientService.toAdd(clientRequestDto));
        assertEquals("Client's postal code is absent", clientException.getMessage());

    }

    @DisplayName("Test to make sure a client is well added to the database")
    @Test
    void testAddClient() {
        ClientRequestDto clientRequestDto = createClientRequestDtoPaulin();
        ClientResponseDto clientResponseDto = createClientResponseDtoPaulin();
        Client clientBefore = createClientWallace();
        Client clientAfter = createClientWallace();

        Mockito.when(mapperMock.toClient(clientRequestDto)).thenReturn(clientBefore);
        Mockito.when(daoMock.save(clientBefore)).thenReturn(clientAfter);
        Mockito.when(mapperMock.toClientResponseDto(clientAfter)).thenReturn(clientResponseDto);

        assertSame(clientResponseDto, clientService.toAdd(clientRequestDto));
        Mockito.verify(daoMock, Mockito.times(1)).save(clientBefore);

    }



}