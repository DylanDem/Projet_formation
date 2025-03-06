package com.accenture.controller;


import com.accenture.service.CarService;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);
    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    List<CarResponseDto> cars() {
        return carService.toFindAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<CarResponseDto> aCar(@PathVariable("id") int id) {
        logger.info("Entering the aCar method with ID: {}", id);
        CarResponseDto found = carService.toFind(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> del(@PathVariable("id") int id) {
        logger.info("Entering the del method with ID: {}", id);
        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid CarRequestDto carRequestDto) {
        logger.info("Entering the add method");
        CarResponseDto registrdCar = carService.toAdd(carRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdCar.id())
                .toUri();

        logger.info("Car added with ID: {}", registrdCar.id());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<CarResponseDto> partiallyUpdate(@PathVariable("id") int id, @RequestBody CarRequestDto carRequestDto) {
        logger.info("Entering the partiallyUpdate method with ID: {}", id);
        CarResponseDto answer = carService.toPartiallyUpdate(id, carRequestDto);
        return ResponseEntity.ok(answer);

    }


}
