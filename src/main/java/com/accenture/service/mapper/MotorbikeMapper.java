package com.accenture.service.mapper;

import com.accenture.repository.entity.Motorbike;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MotorbikeMapper {

    Motorbike toMotorbike(MotorbikeRequestDto motorbikeRequestDto);
    MotorbikeResponseDto toMotorbikeResponseDto (Motorbike motorbike);
}
