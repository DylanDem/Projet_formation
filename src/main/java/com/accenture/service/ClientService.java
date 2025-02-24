package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.model.Licences;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ClientService {
    ClientResponseDto toFind(String email) throws EntityNotFoundException;

    List<ClientResponseDto> toFindAll(String email, String password);

    ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException;

    void saveClient(ClientRequestDto clientRequestDto);

    ClientResponseDto toUpdate(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    ClientResponseDto toPartiallyUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    void delete(String email, String password) throws ClientException;

    List<ClientResponseDto> search(String email, String firstName, String name, LocalDate birthDate, String street, String postalCode, String town, boolean inactive, List<Licences> licencesList, LocalDate registrationDate);
}
