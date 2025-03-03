package com.accenture.service;

import com.accenture.exception.VehicleException;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface MotorbikeService {

    MotorbikeResponseDto toAdd(MotorbikeRequestDto motorbikeRequestDto) throws VehicleException;
    MotorbikeResponseDto toPartiallyUpdate(int id, MotorbikeRequestDto motorbikeRequestDto) throws VehicleException, EntityNotFoundException;
    void delete(int id) throws EntityNotFoundException;
    List<MotorbikeResponseDto> toFindAll();
    MotorbikeResponseDto toFind(int id) throws EntityNotFoundException;
}
