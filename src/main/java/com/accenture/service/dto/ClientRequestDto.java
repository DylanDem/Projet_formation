package com.accenture.service.dto;

import com.accenture.model.Licences;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;


public record ClientRequestDto(
        @NotBlank(message = "Name is mandatory")
        String name,

        @NotBlank(message = "Name is mandatory")
        String firstName,

        @NotBlank(message = "Address is mandatory")
        AddressDto address,

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email HAS to be valid")
        @Column(unique = true)
        String email,

        @NotBlank(message = "Password is mandatory")
        @Size(min = 8, max = 16, message = "Password has to contain between 8 and 16 units.")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$", message = "Passwords needs to contain atleast one of the following elements : Uppercase. Lowercase. One Digit and one special case such as : & # @ - _ §.")
        String password,

        @NotNull(message = "Birth date is mandatory.")
        @Past(message = "User HAS to be over 18 years old.")
        LocalDate birthDate,

        List<Licences> licencesList


) {
}

