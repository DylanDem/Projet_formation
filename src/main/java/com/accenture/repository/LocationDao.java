package com.accenture.repository;

import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationDao extends JpaRepository<Location, Integer> {

    List<Location> findByVehicleId(int id);
}
