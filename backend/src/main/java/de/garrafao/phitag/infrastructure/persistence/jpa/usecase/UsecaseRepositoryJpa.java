package de.garrafao.phitag.infrastructure.persistence.jpa.usecase;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.usecase.Usecase;

public interface UsecaseRepositoryJpa extends JpaRepository<Usecase, String> {

    Optional<Usecase> findByName(final String name);
    
}
