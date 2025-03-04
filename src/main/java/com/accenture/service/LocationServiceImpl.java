package com.accenture.service;

import com.accenture.repository.ClientDao;
import com.accenture.repository.LocationDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Vehicle;
import com.accenture.service.dto.LocationRequestDto;
import com.accenture.service.dto.LocationResponseDto;
import com.accenture.service.mapper.LocationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class LocationServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MotorbikeServiceImpl.class);
    private static final String NULLABLE_ID = "Non present ID";
    private final LocationDao locationDao;
    private final LocationMapper locationMapper;
    private final ClientDao clientDao;

    public LocationServiceImpl(LocationDao locationDao, LocationMapper locationMapper, ClientDao clientDao) {
        this.locationDao = locationDao;
        this.locationMapper = locationMapper;
        this.clientDao = clientDao;
    }


//    public LocationResponseDto reserveation(String email, int idVehicle, LocationRequestDto locationRequestDto) {
//        Client client = clientDao.findById(email).orElseThrow(() -> new EntityNotFoundException("Client not found"));
//        Location location = locationMapper.toLocation(locationRequestDto);
//        location.setClient(client);
//        Vehicle vehicle
//        location.setVehicle();
//
//        locationDao.save(locationRequestDto);
//    }

    //TODO : récupérer véhicule, le save, récupérer réponse, la convertir en ResponseDto, la return
}
