package com.accenture.service.dto;

import com.accenture.model.Fuels;
import com.accenture.model.Licences;
import com.accenture.model.Types;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CarRequestDto(
        @NotBlank(message = "Brand is mandatory")
        String brand,

        @NotBlank(message = "Model is mandatory")
        String model,

        @NotBlank(message = "Color is mandatory")
        String color,

        @Min(2)
        int placesNb,

        List<Fuels> fuelsList,

        List<Licences> licencesList,

        Types types,

        @Min(3)
        int doorsNb,

        @NotBlank(message = "Transmission is mandatory")
        String transmission,

        int luggagePiecesNb,

        boolean airConditioner,

        int dailyLocationPrice,
        int kilometers,
        boolean active,
        boolean outCarPark


) {
}
