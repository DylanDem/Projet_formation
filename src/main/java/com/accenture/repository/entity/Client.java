package com.accenture.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Client {

    @Id
    private String nom;
    private String prenom;
    private Adresse adresse;
    private String email;
    private String password;
    private LocalDate dateDeNaissance;
    private LocalDate dateInscription;
    private List<String> permis;
    private String desactive; // O > actif, N > inactif

}
