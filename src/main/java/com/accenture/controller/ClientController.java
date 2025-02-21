package com.accenture.controller;

import com.accenture.model.Licences;
import com.accenture.repository.ClientDao;
import com.accenture.repository.entity.Client;
import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
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

    @GetMapping("{id}")
    ResponseEntity<Void> del(@PathVariable("id") String email) {
        clientService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto registrdClient = clientService.toAdd(clientRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdClient.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<ClientResponseDto> update(@PathVariable("id") String email, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto answer = clientService.toUpdate(email, clientRequestDto);
        return ResponseEntity.ok(answer);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ClientResponseDto> partiallyUpdate(@PathVariable("id") String email, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto answer = clientService.toUpdate(email, clientRequestDto);
        return ResponseEntity.ok(answer);

    }

//    @GetMapping("/{search}")
//    List<ClientResponseDto> toSearch(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) String firstName,
//            @RequestParam(required = false) Licences licences
//    ) {
//        return clientService.search(name, firstName, licences);
//    }
    //TODO : add location date later
}
