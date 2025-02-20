package com.accenture.repository;

import com.accenture.repository.entity.ConnectedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<ConnectedUser, Long> {
    boolean existsByEmail(String email);
}
