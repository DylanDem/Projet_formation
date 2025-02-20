package com.accenture.repository.entity;


import com.accenture.model.Licences;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends ConnectedUser {


    @NotBlank(message = "adresse is mandatory")
    private Address address;

    @NotNull(message = "birth date is mandatory")
    @Past(message = "user must be over 18 years old")
    private LocalDate birthDate;

    private LocalDate registrationDate = LocalDate.now();

    @Enumerated
    private List<Licences> licencesList;

    private String inactive = "N"; // O > active, N > inactive


}
