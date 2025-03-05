package com.accenture.service.dto;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Vehicle;

import java.time.LocalDate;

public record LocationResponseDto(
        Client client,
        Vehicle vehicle,
        LocalDate startDate,
        LocalDate endDate,
        int travelledKilometers,
        int totalAmountEuros,
        LocalDate validationDate,
        String locationState) {

}
