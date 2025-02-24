package com.accenture.controller;

import com.accenture.service.ClientService;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
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

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    List<ClientResponseDto> clients(String email, String password) {
        return clientService.toFindAll(email, password);
    }

    @DeleteMapping()
    ResponseEntity<Void> del(String email, String password) {
        clientService.delete(email, password);
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
    ResponseEntity<ClientResponseDto> update(@PathVariable("id") String email, String password, @RequestBody @Valid ClientRequestDto clientRequestDto) {
        ClientResponseDto answer = clientService.toUpdate(email, password, clientRequestDto);
        return ResponseEntity.ok(answer);
    }

    @PatchMapping
    ResponseEntity<ClientResponseDto> partiallyUpdate(String email, String password, @RequestBody ClientRequestDto clientRequestDto) {
        ClientResponseDto answer = clientService.toUpdate(email, password, clientRequestDto);
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
