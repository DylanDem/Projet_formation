package com.accenture.service.mapper;

import com.accenture.repository.entity.Car;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Car and CarRequestDto/CarResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface CarMapper {


    /**
     * Converts a CarRequestDto object to a Car object.
     *
     * @param carRequestDto The CarRequestDto object to convert
     * @return The converted Car object
     */
    Car toCar(CarRequestDto carRequestDto);

    /**
     * Converts a Car object to a CarResponseDto object.
     *
     * @param car The Car object to convert
     * @return The converted CarResponseDto object
     */
    CarResponseDto toCarResponseDto (Car car);
}
