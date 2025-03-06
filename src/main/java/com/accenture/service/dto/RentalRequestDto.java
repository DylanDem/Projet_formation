package com.accenture.service.dto;

import com.accenture.model.Accessory;

import java.time.LocalDate;

public record RentalRequestDto(

    int idVehicle,
    Accessory accessory,
    LocalDate startDate,
    LocalDate endDate,
    int travelledKilometers,
    int totalAmountEuros,
    LocalDate validationDate,
    String locationState)


{}
