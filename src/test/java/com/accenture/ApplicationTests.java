package com.accenture;

import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Address;
import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.ClientServiceImpl;
import com.accenture.service.dto.ClientResponseDto;
import com.accenture.service.mapper.ClientMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationTests {


	@Mock
	ClientDao daoMock;

	@Mock
	ClientMapper mapperMock;

	@InjectMocks
	ClientServiceImpl clientService;

	private static Client createClient() {
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

	private static ClientResponseDto createClientResponseDtoFalse() {
		return new ClientResponseDto(
				"Tessier", "Tatiana", new Address("street", "postalCode", "streettown"),
				"tessiertiana@gmail.com", LocalDate.of(1999, 4, 30), LocalDate.now(), List.of(Licences.B1), false);
	}

	private static ClientResponseDto createClientResponseDtoTrue() {
		return new ClientResponseDto(
				"Novotny", "Paulin" , new Address("street2", "postalCode2", "streetFeur"),
				"Novotnypaulin@gmail.com", LocalDate.of(1987, 11, 2), LocalDate.now(), List.of(Licences.B), true);

	}

//	@DisplayName("""
//			For findByEmail(), throws an exception when the client doesn't exist in database
//			""")
//	@Test






}