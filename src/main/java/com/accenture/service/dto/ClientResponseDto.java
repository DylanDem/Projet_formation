package com.accenture.service.dto;

import com.accenture.model.Licences;


import java.time.LocalDate;
import java.util.List;

public record ClientResponseDto(


        String name,
        String firstName,
        AddressDto address,
        String email,
        LocalDate birthDate,
        LocalDate registrationDate,
        List<Licences> licencesList,
        boolean inactive
) {
}
