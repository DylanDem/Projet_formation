package com.accenture.service.mapper;

import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting between Client and ClientRequestDto/ClientResponseDto objects.
 * Utilizes AddressMapper for mapping address-related fields.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ClientMapper {


    /**
     * Converts a ClientRequestDto object to a Client object.
     *
     * @param clientRequestDto The ClientRequestDto object to convert
     * @return The converted Client object
     */
    Client toClient(ClientRequestDto clientRequestDto);

    /**
     * Converts a Client object to a ClientResponseDto object.
     *
     * @param client The Client object to convert
     * @return The converted ClientResponseDto object
     */
    ClientResponseDto toClientResponseDto(Client client);
}
