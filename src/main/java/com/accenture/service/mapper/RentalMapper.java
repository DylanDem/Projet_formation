package com.accenture.service.mapper;

import com.accenture.repository.entity.Rental;
import com.accenture.service.dto.RentalRequestDto;
import com.accenture.service.dto.RentalResponseDto;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting between Rental and RentalRequestDto/RentalResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper {


    /**
     * Converts a RentalRequestDto object to a Rental object.
     *
     * @param rentalRequestDto The RentalRequestDto object to convert
     * @return The converted Rental object
     */
    Rental toLocation(RentalRequestDto rentalRequestDto);

    /**
     * Converts a Rental object to a RentalResponseDto object.
     *
     * @param rental The Rental object to convert
     * @return The converted RentalResponseDto object
     */
    RentalResponseDto toLocationResponseDto(Rental rental);
}
