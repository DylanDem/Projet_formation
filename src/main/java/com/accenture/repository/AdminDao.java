package com.accenture.repository;

import com.accenture.repository.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, String> {

    boolean existsByEmail(String email);

    Optional<Admin> findByEmailContaining(String email);

    List<Admin> findByFirstNameContaining(String firstName);

    List<Admin> findByNameContaining(String name);

    List<Admin> findByFunctionContaining(String function);
}

