package com.accenture.repository.entity;

import com.accenture.model.Types;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;
    private String model;
    private String color;

    @Enumerated(EnumType.STRING)
    private Types types;

    private int dailyLocationPrice;
    private int kilometers;
    private boolean active;
    private boolean outCarPark;
}
