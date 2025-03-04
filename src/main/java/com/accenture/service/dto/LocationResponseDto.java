package com.accenture.service.dto;

import com.accenture.repository.entity.Location;

public record LocationResponseDto(String email, int idVehicle, Location location) {

}
