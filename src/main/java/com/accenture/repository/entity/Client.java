package com.accenture.repository.entity;


import com.accenture.model.Licences;
import jakarta.persistence.*;
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
@DiscriminatorValue("Client")
public class Client extends ConnectedUser {

    @OneToOne(cascade = CascadeType.ALL)
    @NotBlank(message = "adresse is mandatory")
    private Address address;

    @NotNull(message = "birth date is mandatory")
    @Past(message = "user must be over 18 years old")
    private LocalDate birthDate;

    private LocalDate registrationDate = LocalDate.now();

    @ElementCollection
    private List<Licences> licencesList;

    private boolean inactive;

}
