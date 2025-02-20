package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface ClientService {
    ClientResponseDto toFind(int id) throws EntityNotFoundException;

    List<ClientRequestDto> toFindAll();

    ClientResponseDto toAdd(ClientRequestDto clientRequestDto) throws ClientException;

    void saveClient(ClientRequestDto clientRequestDto);


}
