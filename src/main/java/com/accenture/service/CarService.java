package com.accenture.service;

import com.accenture.exception.VehicleException;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface CarService {

    CarResponseDto toAdd(CarRequestDto carRequestDto) throws VehicleException;

    CarResponseDto toPartiallyUpdate(int id, CarRequestDto carRequestDto) throws VehicleException, EntityNotFoundException;

    void delete(int id) throws EntityNotFoundException;


    List<CarResponseDto> toFindAll();

    CarResponseDto toFind(int id) throws EntityNotFoundException;
}
