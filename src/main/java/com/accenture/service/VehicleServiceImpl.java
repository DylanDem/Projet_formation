package com.accenture.service;

import com.accenture.repository.CarDao;
import com.accenture.repository.MotorbikeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    CarDao carDao;

    @Autowired
    MotorbikeDao motorbikeDao;

    @Override
    public List<Object> findAllVehicles() {
        List<Object> vehicles = new ArrayList<>();
        vehicles.addAll(carDao.findAll());
        vehicles.addAll(motorbikeDao.findAll());
        return vehicles;
    }
}
