package com.accenture.repository.entity;


import com.accenture.model.Licences;
import com.accenture.model.TypesMotorbike;
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
@DiscriminatorValue("Motorbike")
public class Motorbike extends Vehicle {

    private int cylinders;
    private int cylinderCapacity;
    private String transmission;
    private int weight;
    private int kwPower;
    private int seatHeight;

    @Enumerated(EnumType.STRING)
    private TypesMotorbike typesMotorbike;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Licences> licencesList;



}
