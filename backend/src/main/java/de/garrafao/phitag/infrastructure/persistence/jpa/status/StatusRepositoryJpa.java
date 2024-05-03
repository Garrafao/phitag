package de.garrafao.phitag.infrastructure.persistence.jpa.status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.status.Status;

public interface StatusRepositoryJpa extends JpaRepository<Status, Integer> {
    
    Optional<Status> findByName(String name);
    
}
