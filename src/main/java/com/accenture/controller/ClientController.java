package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDto> clients() {
        return clientService.toFindAll();
    }


    /**
     * Retrieves client information based on their email and password.
     *
     * @param email    The email of the client to retrieve
     * @param password The password of the client to retrieve
     * @return A ClientResponseDto object containing the client's information
     */
    @GetMapping("/myaccount")
    ClientResponseDto optClient(String email, String password) {
        logger.info("Entering the optClient method with email: {}", email);
        return clientService.getInfos(email, password);
    }


    /**
     * Deletes a client based on their email and password.
     *
     * @param email    The email of the client to delete
     * @param password The password of the client to delete
     * @return A ResponseEntity object with HTTP status NO_CONTENT
     */
    @DeleteMapping()
    ResponseEntity<Void> del(String email, String password) {
        logger.info("Entering the del method with email: {}", email);
        clientService.delete(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        logger.info("Entering the add method");
        ClientResponseDto registrdClient = clientService.toAdd(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdClient.email())
                .toUri();

        logger.info("Client added with email: {}", registrdClient.email());
        return ResponseEntity.created(location).build();
    }


    /**
     * Partially updates an existing client based on their email, password, and the provided request data.
     *
     * @param email The email of the client to partially update
     * @param password The password of the client to partially update
     * @param clientRequestDto The client request data to update
     * @return A ResponseEntity object containing the updated ClientResponseDto with HTTP status OK
     */
    @PatchMapping
    ResponseEntity<ClientResponseDto> partiallyUpdate(String email, String password, @RequestBody ClientRequestDto clientRequestDto) {
        logger.info("Entering the partiallyUpdate method with email: {}", email);
        ClientResponseDto answer = clientService.toUpdate(email, password, clientRequestDto);
        return ResponseEntity.ok(answer);
    }
}
