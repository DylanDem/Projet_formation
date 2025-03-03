package com.accenture.repository.entity;


import com.accenture.model.Accessory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    private Vehicle vehicle;

    private Accessory accessory;
    private LocalDate startDate;
    private LocalDate endDate;
    private int travelledKilometers;
    private int totalAmount;
    private LocalDate validationDate;
    private String locationState;





}
