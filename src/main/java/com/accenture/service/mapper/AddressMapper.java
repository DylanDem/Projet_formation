package com.accenture.service.mapper;

import com.accenture.repository.entity.Address;
import com.accenture.service.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressDto addressDto);
    AddressDto toAdressDto (Address address);
}
