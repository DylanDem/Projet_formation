package com.accenture.repository;

import com.accenture.model.Licences;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.ConnectedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientDao extends JpaRepository<Client, String> {
    boolean existsByEmail(String email);

    List<Client> findByEmailContaining(String email);


    List<Client> findByFirstNameContaining(String firstName);

    List<Client> findByNameContaining(String name);

    List<Client> findByBirthDate(LocalDate birthDate);

    List<Client> findByAddress_StreetContaining(int street);

    List<Client> findByAddress_PostalCodeContaining(String postalCode);

    List<Client> findByAddress_TownContaining(String town);

    List<Client> findByInactive(String inactive);

    List<Client> findByRegistrationDate(LocalDate registrationDate);

    List<Client> findByLicencesContaining(Licences permis);
}
