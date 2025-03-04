package com.accenture.service.dto;

import com.accenture.model.Accessory;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Vehicle;

import java.time.LocalDate;

public record LocationRequestDto (

    Client client,
    Vehicle vehicle,
    Accessory accessory,
    LocalDate startDate,
    LocalDate endDate,
    int travelledKilometers,
    int totalAmount,
    LocalDate validationDate,
    String locationState)


{}
