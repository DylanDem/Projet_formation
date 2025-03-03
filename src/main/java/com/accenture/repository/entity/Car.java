package com.accenture.repository.entity;


import com.accenture.model.Fuels;
import com.accenture.model.Licences;
import com.accenture.model.Types;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Car")
public class Car extends Vehicle {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Fuels> fuelsList;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Licences> licencesList;

    @Enumerated(EnumType.STRING)
    private Types types;


    private String transmission;
    private int placesNb;
    private int doorsNb;
    private int luggagePiecesNb;
    private boolean airConditioner;



}
