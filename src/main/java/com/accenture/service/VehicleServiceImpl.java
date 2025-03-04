package com.accenture.service;

import com.accenture.Application;
import com.accenture.repository.CarDao;
import com.accenture.repository.MotorbikeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);


    @Autowired
    CarDao carDao;

    @Autowired
    MotorbikeDao motorbikeDao;

    @Override
    public List<Object> findAllVehicles() {
        logger.info("Entering findAllVehicles method");

        List<Object> vehicles = new ArrayList<>();
        try {
            logger.debug("Fetching all cars from carDao");
            vehicles.addAll(carDao.findAll());

            logger.debug("Fetching all motorbikes from motorbikeDao");
            vehicles.addAll(motorbikeDao.findAll());
        } catch (Exception e) {
            logger.error("Exception occurred while finding all vehicles: {}", e.getMessage(), e);
        }

        logger.info("Exiting findAllVehicles method with {} vehicles found", vehicles.size());
        return vehicles;
    }
}
