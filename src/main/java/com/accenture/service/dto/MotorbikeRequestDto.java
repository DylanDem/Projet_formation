package com.accenture.service.dto;

import com.accenture.model.Licences;
import com.accenture.model.TypesMotorbike;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MotorbikeRequestDto(
        @NotBlank(message = "Brand is mandatory")
        String brand,

        @NotBlank(message = "Model is mandatory")
        String model,

        @NotBlank(message = "Color is mandatory")
        String color,

        @NotBlank(message = "Transmission is mandatory")
        String transmission,

        @NotNull(message = "Cylinders are mandatory")
        int cylinders,

        String cylinderCapacity,

        @NotNull(message = "Weight is mandatory")
        int weight,

        @NotNull(message = "kW's Power mandatory")
        int kwPower,

        @NotNull(message = "seat's height is mandatory")
        int seatHeight,

        TypesMotorbike typesMotorbike,

        List<Licences> licencesList,

        int dailyLocationPrice,
        int kilometers,
        boolean active,
        boolean outCarPark
) {
}
