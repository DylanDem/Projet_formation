package com.accenture.repository;


import com.accenture.repository.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorbikeDao extends JpaRepository<Motorbike, Integer> {


}
