package com.accenture.service.dto;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;

import java.time.LocalDate;

public record LocationResponseDto(
        Client client,
        String email,
        int idVehicle,
        Location location,
        LocalDate startDate,
        LocalDate endDate,
        int travelledKilometers,
        int totalAmountEuros,
        LocalDate validationDate,
        String locationState) {

}
