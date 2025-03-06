package com.accenture.service.mapper;

import com.accenture.repository.entity.Motorbike;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting between Motorbike and MotorbikeRequestDto/MotorbikeResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface MotorbikeMapper {

    /**
     * Converts a MotorbikeRequestDto object to a Motorbike object.
     *
     * @param motorbikeRequestDto The MotorbikeRequestDto object to convert
     * @return The converted Motorbike object
     */
    Motorbike toMotorbike(MotorbikeRequestDto motorbikeRequestDto);

    /**
     * Converts a Motorbike object to a MotorbikeResponseDto object.
     *
     * @param motorbike The Motorbike object to convert
     * @return The converted MotorbikeResponseDto object
     */
    MotorbikeResponseDto toMotorbikeResponseDto (Motorbike motorbike);
}
