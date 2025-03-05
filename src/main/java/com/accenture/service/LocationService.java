package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.service.dto.LocationRequestDto;
import com.accenture.service.dto.LocationResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface LocationService {

    LocationResponseDto addReservation(String email, LocationRequestDto locationRequestDto);
    List<LocationResponseDto> toFindAll();
    LocationResponseDto toFind(int id) throws EntityNotFoundException;
    void delete(int id) throws EntityNotFoundException;
    LocationResponseDto toPartiallyUpdate(int id, LocationRequestDto locationRequestDto) throws LocationException, EntityNotFoundException;
}
