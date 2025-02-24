package com.accenture;

import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.ClientServiceImpl;
import com.accenture.service.mapper.ClientMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApplicationTests {


	@Mock
	ClientDao daoMock;

	@Mock
	ClientMapper mapperMock;

	@InjectMocks
	ClientServiceImpl clientService;

	private static Client creerClient() {
		Client c = new Client();
		c.setName("Tessier");
		c.setFirstName("Tatiana");
		c.setEmail("tessiert@gmail.com");
		c.setPassword("Tttessier324$");
		return c;

	}

}