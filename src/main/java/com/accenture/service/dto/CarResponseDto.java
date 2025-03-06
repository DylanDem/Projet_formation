package com.accenture.service.dto;

import com.accenture.model.Fuels;
import com.accenture.model.Licences;
import com.accenture.model.TypeVehicleEnum;
import com.accenture.model.Types;

import java.util.List;

public record CarResponseDto(

        int id,
        TypeVehicleEnum typeVehicleEnum,
        String brand,
        String model,
        String color,
        int placesNb,
        List<Fuels> fuelsList,
        List<Licences> licencesList,
        Types types,
        int doorsNb,
        String transmission,
        int luggagePiecesNb,
        boolean airConditioner
) {
}
