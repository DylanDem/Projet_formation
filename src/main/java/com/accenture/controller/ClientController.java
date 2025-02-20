package com.accenture.controller;

import com.accenture.repository.ClientDao;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDto> clients() {
        return clientService.toFindAll();
    }

}
