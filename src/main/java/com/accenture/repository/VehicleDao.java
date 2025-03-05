package com.accenture.repository;

import com.accenture.repository.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDao extends JpaRepository<Vehicle, Integer> {
}
