package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Licences;
import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    ClientResponseDto toFind(String email) throws EntityNotFoundException;

    List<ClientResponseDto> toFindAll();

    ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException;

    void saveClient(ClientRequestDto clientRequestDto);

    ClientResponseDto toUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    ClientResponseDto toPartiallyUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    void delete(String email) throws ClientException;

    List<ClientResponseDto> search(String email, String firstName, String name, LocalDate birthDate, String street, String postalCode, String town, String inactive, List<Licences> licencesList, LocalDate registrationDate);
}
