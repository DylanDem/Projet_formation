package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.service.dto.RentalRequestDto;
import com.accenture.service.dto.RentalResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface RentalService {

    RentalResponseDto addReservation(String email, RentalRequestDto rentalRequestDto);
    List<RentalResponseDto> toFindAll();
    RentalResponseDto toFind(int id) throws EntityNotFoundException;
    void delete(int id) throws EntityNotFoundException;
    RentalResponseDto toPartiallyUpdate(int id, RentalRequestDto rentalRequestDto) throws LocationException, EntityNotFoundException;
}
