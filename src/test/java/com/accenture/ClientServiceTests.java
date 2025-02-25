package com.accenture;

import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Address;
import com.accenture.repository.entity.Client;
import com.accenture.service.ClientServiceImpl;
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

import javax.swing.text.html.Option;
import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
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
                "Tessier", "Tatiana", new Address("street", "postalCode", "streettown"),
                "tessiertiana@gmail.com", LocalDate.of(1999, 4, 30), LocalDate.now(), List.of(Licences.B1), false);
    }

    private static ClientResponseDto createClientResponseDtoPaulin() {
        return new ClientResponseDto(
                "Novotny", "Paulin", new Address("street2", "postalCode2", "streetFeur"),
                "Novotnypaulin@gmail.com", LocalDate.of(1987, 11, 2), LocalDate.now(), List.of(Licences.B), true);

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


//    @DisplayName("""
//            find(email) throws a clientResponseDto whenever the client IS registered in the database)
//            """)
//    @Test
//    void testDoesExist() {
//        Client c = createClientWallace();
//        Optional<Client> optClient = Optional.of(c);
//        Mockito.when(daoMock.findByEmail("Novotnypaulin@gmail.com")).thenReturn(optClient);
//        ClientResponseDto dto = createClientResponseDtoPaulin();
//        Mockito.when(mapperMock.toClientResponseDto(c)).thenReturn(dto);
//        assertSame(dto, clientService.toFind("Novotnypaulin@gmail.com"));
//    }

}