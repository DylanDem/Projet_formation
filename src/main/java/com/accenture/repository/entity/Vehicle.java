package com.accenture.repository.entity;

import com.accenture.model.TypeVehicleEnum;
import com.accenture.model.Types;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.EnumMapping;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Enumerated(EnumType.STRING)
    private TypeVehicleEnum typeVehicleEnum;

    private String brand;
    private String model;
    private String color;

    private int dailyLocationPrice;
    private int kilometers;


    private Boolean active;
    private Boolean outCarPark;
}
