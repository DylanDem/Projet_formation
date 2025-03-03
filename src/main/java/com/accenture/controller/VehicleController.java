package com.accenture.controller;

import com.accenture.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/all")
    public List<Object> getAllVehicles() {
        return vehicleService.findAllVehicles();
    }
}
