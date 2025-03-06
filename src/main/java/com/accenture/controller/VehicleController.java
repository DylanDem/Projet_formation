package com.accenture.controller;

import com.accenture.repository.entity.Vehicle;
import com.accenture.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);


    /**
     * Retrieves a list of all vehicles.
     *
     * @return A list of all vehicles
     */
    @GetMapping("/all")
    public List<Object> getAllVehicles() {
        logger.info("Entering the getAllVehicles method");
        return vehicleService.findAllVehicles();
    }


    @Operation(summary = "Find vehicles by search")
    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> search(@RequestParam(required = false) Boolean active, @RequestParam(required = false) Boolean outCarPark) {
        logger.info("Entering the search method with active: {}, outCarPark: {}", active, outCarPark);
        List<Vehicle> vehicles = vehicleService.search(active, outCarPark);

        return ResponseEntity.ok(vehicles);
    }

}
