package com.accenture.service;

import com.accenture.exception.VehicleException;
import com.accenture.repository.CarDao;
import com.accenture.repository.entity.Car;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import com.accenture.service.mapper.CarMapper;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;
    private final CarMapper carMapper;

    public CarServiceImpl(CarDao carDao, CarMapper carMapper) {
        this.carDao = carDao;
        this.carMapper = carMapper;
    }


    private static void carVerify(CarRequestDto carRequestDto) throws VehicleException {
        if (carRequestDto == null)
            throw new VehicleException("ClientRequestDto is null");
        if (carRequestDto.brand() == null || carRequestDto.brand().isBlank())
            throw new VehicleException("car's brand is absent");
        if (carRequestDto.model() == null || carRequestDto.model().isBlank())
            throw new VehicleException("car's model is absent");
        if (carRequestDto.color() == null || carRequestDto.color().isBlank())
            throw new VehicleException("car's color is absent");
        if (carRequestDto.placesNb() < 2)
            throw new VehicleException("car's places number is insufficient");
        if (carRequestDto.fuelsList() == null)
            throw new VehicleException("car's fuel is absent");
        if (carRequestDto.licencesList() == null)
            throw new VehicleException("car's licence is absent");
        if (carRequestDto.typesList() == null)
            throw new VehicleException("car's type is absent");
        if (carRequestDto.doorsNb() < 3)
            throw new VehicleException("car's doors number is insufficient");
        if (carRequestDto.transmission() == null || carRequestDto.transmission().isBlank())
            throw new VehicleException("car's transmission is absent");
        if (carRequestDto.luggagePiecesNb() == 0)
            throw new VehicleException("car's luggage's pieces number is absent");
    }

//    public CarResponseDto toAdd(CarRequestDto carRequestDto) throws VehicleException {
//        carVerify(carRequestDto);
//        Car car = carMapper
//    }
    //TODO : FINIR METHODE

}
