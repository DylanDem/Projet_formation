package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
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
@RequestMapping("/admins")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    List<AdminResponseDto> admins(String email, String password) {
        return adminService.toFindAll(email, password);
    }


    /**
     * Deletes an admin based on their email and password.
     *
     * @param email    The email of the admin to delete
     * @param password The password of the admin to delete
     * @return A ResponseEntity object with HTTP status NO_CONTENT
     */
    @DeleteMapping
    ResponseEntity<Void> del(String email, String password) {
        logger.info("Entering the delete admin method");
        adminService.deleteAdmin(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        logger.info("Entering the add method");
        AdminResponseDto registrdAdmin = adminService.toAddMin(adminRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdAdmin.email())
                .toUri();


        logger.info("Admin added with email: {}", registrdAdmin.email());
        return ResponseEntity.created(location).build();
    }


    @PatchMapping
    ResponseEntity<AdminResponseDto> partiallyUpdate(String email, String password, @RequestBody AdminRequestDto adminRequestDto) {
        logger.info("Entering the partiallyUpdate method with email: {}", email);
        AdminResponseDto answer = adminService.toUpdateAdmin(email, password, adminRequestDto);
        return ResponseEntity.ok(answer);

    }


}
