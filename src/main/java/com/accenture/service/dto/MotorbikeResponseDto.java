package com.accenture.service.dto;

import com.accenture.model.Licences;
import com.accenture.model.TypeVehicleEnum;
import com.accenture.model.TypesMotorbike;

import java.util.List;

public record MotorbikeResponseDto(

        int id,
        TypeVehicleEnum typeVehicleEnum,
        String brand,
        String model,
        String color,
        List<Licences> licencesList,
        TypesMotorbike typesMotorbike,
        String transmission,
        int cylinders,
        int cylinderCapacity,
        int weight,
        int kwPower,
        int seatHeight

        ) {
}
