package com.accenture.service;

import com.accenture.exception.ClientException;

import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;

public interface ClientService {
    ClientResponseDto toFind(String email) throws EntityNotFoundException;

    List<ClientResponseDto> toFindAll();

    ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException;

    ClientResponseDto toUpdate(String email, String password, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    ClientResponseDto toPartiallyUpdate(String email, ClientRequestDto clientRequestDto) throws ClientException, EntityNotFoundException;

    void delete(String email, String password) throws ClientException;

    ClientResponseDto getInfos (String email, String password);
}
