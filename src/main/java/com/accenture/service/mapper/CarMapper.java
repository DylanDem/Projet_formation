package com.accenture.service.mapper;

import com.accenture.repository.entity.Car;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    Car toCar(CarRequestDto carRequestDto);
    CarResponseDto toCarResponseDto (Car car);
}
