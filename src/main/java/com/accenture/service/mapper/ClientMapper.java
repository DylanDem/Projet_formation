package com.accenture.service.mapper;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.ConnectedUser;
import com.accenture.service.dto.ClientRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toClient(ClientRequestDto clientRequestDto);
    ClientRequestDto toClientResponseDto (ConnectedUser client);
}
