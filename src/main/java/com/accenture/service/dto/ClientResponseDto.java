package com.accenture.service.dto;

import com.accenture.model.Licences;
import com.accenture.repository.entity.Address;

import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto(

        Long id,
        String name,
        String firstName,
        Address address,
        String email,
        LocalDate birthDate,
        LocalDate registrationDate,
        Licences licence,
        String inactive  // "O" active, "N" inactive
) {}
