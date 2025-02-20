package com.accenture.controller;

import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // BindingResult sert principalement à valider des formulaires
    @PostMapping("/registration")
    public ResponseEntity<ClientResponseDto> clientRegistration(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto responseDto = clientService.saveClient(clientRequestDto);
    } return ResponseEntity.ok(responseDto);

}
