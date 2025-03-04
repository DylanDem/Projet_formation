package com.accenture.service.mapper;

import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Motorbike;
import com.accenture.service.dto.LocationRequestDto;
import com.accenture.service.dto.LocationResponseDto;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toLocation(LocationRequestDto locationRequestDto);
    LocationResponseDto toLocationResponseDto (Location location);
}
