package com.accenture.controller;

import com.accenture.service.LocationService;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import com.accenture.service.dto.LocationRequestDto;
import com.accenture.service.dto.LocationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    List<LocationResponseDto> locations() {
        return locationService.toFindAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<LocationResponseDto> aLocation(@PathVariable("id") int id) {
        LocationResponseDto found = locationService.toFind(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> del(@PathVariable("id") int id) {
        locationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid String email, int idVehicle, LocationRequestDto locationRequestDto) {
        LocationResponseDto registrdLocation = locationService.addReservation(email, idVehicle, locationRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdLocation.idVehicle())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    ResponseEntity<LocationResponseDto> partiallyUpdate(@PathVariable("id") int id, @RequestBody LocationRequestDto locationRequestDto) {
        LocationResponseDto answer = locationService.toPartiallyUpdate(id, locationRequestDto);
        return ResponseEntity.ok(answer);

    }
}
