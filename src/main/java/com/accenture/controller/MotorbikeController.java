package com.accenture.controller;

import com.accenture.service.MotorbikeService;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
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
@RequestMapping("/motorbikes")
public class MotorbikeController {

    private static final Logger logger = LoggerFactory.getLogger(MotorbikeController.class);
    private MotorbikeService motorbikeService;

    public MotorbikeController(MotorbikeService motorbikeService) {
        this.motorbikeService = motorbikeService;
    }

    @GetMapping
    List<MotorbikeResponseDto> motorbikes() {
        return motorbikeService.toFindAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<MotorbikeResponseDto> aMotorbike(@PathVariable("id") int id) {
        logger.info("Entering the aMotorbike method with ID: {}", id);
        MotorbikeResponseDto found = motorbikeService.toFind(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> del(@PathVariable("id") int id) {
        logger.info("Entering the del method with ID: {}", id);
        motorbikeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid MotorbikeRequestDto motorbikeRequestDto) {
        logger.info("Entering the add method");
        MotorbikeResponseDto registrdMotorbike = motorbikeService.toAdd(motorbikeRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdMotorbike.id())
                .toUri();

        logger.info("Motorbike added with ID: {}", registrdMotorbike.id());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<MotorbikeResponseDto> partiallyUpdate(@PathVariable("id") int id, @RequestBody MotorbikeRequestDto motorbikeRequestDto) {
        logger.info("Entering the partiallyUpdate method with ID: {}", id);
        MotorbikeResponseDto answer = motorbikeService.toPartiallyUpdate(id, motorbikeRequestDto);
        return ResponseEntity.ok(answer);
    }
}
