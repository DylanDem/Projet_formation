package com.accenture.repository.entity;

import com.accenture.model.TypeVehicleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
