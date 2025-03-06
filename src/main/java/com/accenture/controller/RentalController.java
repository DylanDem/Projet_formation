package com.accenture.controller;

import com.accenture.service.RentalService;
import com.accenture.service.dto.RentalRequestDto;
import com.accenture.service.dto.RentalResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private static final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    List<RentalResponseDto> rentals() {
        return rentalService.toFindAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<RentalResponseDto> aRental(@PathVariable("id") int id) {
        logger.info("Entering the aRental method with ID: {}", id);
        RentalResponseDto found = rentalService.toFind(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> del(@PathVariable("id") int id) {
        logger.info("Entering the del method with ID: {}", id);
        rentalService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(String email, @RequestBody @Valid RentalRequestDto rentalRequestDto) {
        logger.info("Entering the add method with email: {}", email);
        RentalResponseDto registrdRental = rentalService.addReservation(email, rentalRequestDto);
        URI rental = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdRental.vehicle().getId())
                .toUri();

        logger.info("Rental added with vehicle ID: {}", registrdRental.vehicle().getId());
        return ResponseEntity.created(rental).build();
    }

    /**
     * Partially updates an existing rental based on its ID and the provided request data.
     *
     * @param id The ID of the rental to partially update
     * @param rentalRequestDto The rental request data to update
     * @return A ResponseEntity object containing the updated RentalResponseDto with HTTP status OK
     */
    @PatchMapping("/{id}")
    ResponseEntity<RentalResponseDto> partiallyUpdate(@PathVariable("id") int id, @RequestBody RentalRequestDto rentalRequestDto) {
        logger.info("Entering the partiallyUpdate method with ID: {}", id);
        RentalResponseDto answer = rentalService.toPartiallyUpdate(id, rentalRequestDto);
        return ResponseEntity.ok(answer);

    }
}
