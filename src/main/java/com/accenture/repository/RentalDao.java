package com.accenture.repository;

import com.accenture.repository.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalDao extends JpaRepository<Rental, Integer> {

    List<Rental> findByVehicleId(int id);
}
