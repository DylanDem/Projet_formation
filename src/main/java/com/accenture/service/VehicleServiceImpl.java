package com.accenture.service;

import com.accenture.repository.CarDao;
import com.accenture.repository.MotorbikeDao;
import com.accenture.repository.entity.Car;
import com.accenture.repository.entity.Motorbike;
import com.accenture.repository.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);


    CarDao carDao;
    MotorbikeDao motorbikeDao;

    public VehicleServiceImpl(CarDao carDao, MotorbikeDao motorbikeDao) {
        this.carDao = carDao;
        this.motorbikeDao = motorbikeDao;
    }

    /**
     * Retrieves a list of all vehicles.
     *
     * @return A list of all vehicles
     */
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


    /**
     * Searches for vehicles based on their active status and location.
     *
     * @param active Optional parameter to filter vehicles based on their active status
     * @param outCarPark Optional parameter to filter vehicles based on their location being out of the car park
     * @return A list of vehicles matching the search criteria
     */
    @Override
    public List<Vehicle> search(Boolean active, Boolean outCarPark) {
        logger.info("Entering the search method with active={} and outCarPark={}", active, outCarPark);
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            logger.debug("Retrieving all cars");
            List<Car> car = carDao.findAll();
            logger.debug("Retrieving all motorcycles");
            List<Motorbike> motorbike = motorbikeDao.findAll();
            vehicles.addAll(filterVehicles(car, active, outCarPark));
            vehicles.addAll(filterVehicles(motorbike, active, outCarPark));
        } catch (Exception e) {
            logger.error("An exception occurred while searching for all vehicles: {}", e.getMessage(), e);
        }
        logger.info("Exiting the search method with {} vehicles found", vehicles.size());
        return vehicles;
    }

    private <T extends Vehicle> List<T> filterVehicles(List<T> vehicles, Boolean active, Boolean outCarPark) {
        return vehicles.stream().filter(vehicle -> (active == null || vehicle.getActive().equals(active)) &&
                (outCarPark == null || vehicle.getOutCarPark().equals(outCarPark))).toList();
    }


}
