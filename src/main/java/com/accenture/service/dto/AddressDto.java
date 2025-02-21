package com.accenture.service.dto;

public record AddressDto(

        int id,
        String street,
        String postalCode,
        String town
) {
}
