package com.accenture.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@NoArgsConstructor
@AllArgsConstructor
public abstract class ConnectedUser {

    @Id
    private String email;
    private String name;
    private String firstName;
    private String password;





}
