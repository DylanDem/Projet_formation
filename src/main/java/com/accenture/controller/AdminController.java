package com.accenture.controller;

import com.accenture.service.AdminService;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import com.accenture.service.dto.ClientRequestDto;
import com.accenture.service.dto.ClientResponseDto;
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

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    List<AdminResponseDto> admins() {
        return adminService.toFindAll();
    }

    @GetMapping("{id}")
    ResponseEntity<Void> del(@PathVariable("id") String email) {
        adminService.delete(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    ResponseEntity<Void> add(@RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto registrdAdmin = adminService.toAdd(adminRequestDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registrdAdmin.email())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<AdminResponseDto> update(@PathVariable("id") String email, @RequestBody @Valid AdminRequestDto adminRequestDto) {
        AdminResponseDto answer = adminService.toUpdate(email, adminRequestDto);
        return ResponseEntity.ok(answer);
    }

    @PatchMapping("/{id}")
    ResponseEntity<AdminResponseDto> partiallyUpdate(@PathVariable("id") String email, @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto answer = adminService.toUpdate(email, adminRequestDto);
        return ResponseEntity.ok(answer);

    }


    }
