package com.accenture.repository.entity;


import com.accenture.model.Licences;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@DiscriminatorValue("ROLE_CLIENT")
public class Client extends ConnectedUser {

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    private LocalDate birthDate;

    private LocalDate registrationDate = LocalDate.now();

    @ElementCollection
    private List<Licences> licencesList;

    private boolean inactive;

    public Client(String email, String name, String firstName, String password, Address address, LocalDate birthDate, LocalDate registrationDate, List<Licences> licencesList, boolean inactive) {
        super(email, name, firstName, password);
        this.address = address;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.licencesList = licencesList;
        this.inactive = inactive;
    }




}
