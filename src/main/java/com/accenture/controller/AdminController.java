package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.LocationServiceImpl;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
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
@RequestMapping("/admins")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);


    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    List<AdminResponseDto> admins(String email, String password) {
        return adminService.toFindAll(email, password);
    }

    @DeleteMapping
    ResponseEntity<Void> del( String email, String password) {
        logger.info("Entering the delete admin method");
        adminService.deleteAdmin(email, password);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto registrdAdmin = adminService.toAddMin(adminRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdAdmin.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<AdminResponseDto> update(@PathVariable("id") String email, String password, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto answer = adminService.toUpdateAdmin(email, password, adminRequestDto);
        return ResponseEntity.ok(answer);
    }

    @PatchMapping
    ResponseEntity<AdminResponseDto> partiallyUpdate(String email, String password, @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto answer = adminService.toUpdateAdmin(email, password, adminRequestDto);
        return ResponseEntity.ok(answer);

    }


    }
