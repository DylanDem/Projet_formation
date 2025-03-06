package com.accenture.service.mapper;

import com.accenture.repository.entity.Address;
import com.accenture.service.dto.AddressDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Address and AddressDto objects.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    /**
     * Converts an AddressDto object to an Address object.
     *
     * @param addressDto The AddressDto object to convert
     * @return The converted Address object
     */
    Address toAddress(AddressDto addressDto);


    /**
     * Converts an Address object to an AddressDto object.
     *
     * @param address The Address object to convert
     * @return The converted AddressDto object
     */
    AddressDto toAdressDto (Address address);
}
