package com.accenture.service.dto;

public record AddressDto(

        int id,
        int street,
        String postalCode,
        String town
) {
}
