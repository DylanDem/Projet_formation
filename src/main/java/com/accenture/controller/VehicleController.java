package com.accenture.controller;

import com.accenture.repository.entity.Vehicle;
import com.accenture.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @Operation(summary = "Find vehicles by search")
    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> search(@RequestParam(required = false) Boolean active, @RequestParam(required = false) Boolean outCarPark) {
        List<Vehicle> vehicles = vehicleService.search(active, outCarPark);

        return ResponseEntity.ok(vehicles);
    }

}
