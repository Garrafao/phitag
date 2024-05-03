package de.garrafao.phitag.infrastructure.persistence.jpa.visibility;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.visibility.Visibility;

public interface VisibilityRepositoryJpa extends JpaRepository<Visibility, Integer> {
    
    Optional<Visibility> findByName(String name);
    
}
