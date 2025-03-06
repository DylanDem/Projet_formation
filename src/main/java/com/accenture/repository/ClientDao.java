package com.accenture.repository;

import com.accenture.model.Licences;
import com.accenture.repository.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, String> {
    boolean existsByEmail(String email);

    List<Client> findByEmailContaining(String email);

    Optional<Client> findByEmail(String email);

    List<Client> findByFirstNameContaining(String firstName);

    List<Client> findByNameContaining(String name);

    List<Client> findByBirthDate(LocalDate birthDate);

    List<Client> findByAddress_StreetContaining(String street);

    List<Client> findByAddress_PostalCodeContaining(String postalCode);

    List<Client> findByAddress_TownContaining(String town);

    List<Client> findByInactive(boolean inactive);

    List<Client> findByRegistrationDate(LocalDate registrationDate);

    List<Client> findByLicencesListContaining(Licences licences);
}
