package com.accenture.service;

import com.accenture.repository.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<Object> findAllVehicles();

    List<Vehicle> search(Boolean active, Boolean outCarPark);
}
