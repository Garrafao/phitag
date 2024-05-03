package de.garrafao.phitag.infrastructure.persistence.jpa.sampling;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.sampling.Sampling;

public interface SamplingRepositoryJpa extends JpaRepository<Sampling, String> {
    
    Optional<Sampling> findByName(String name);
}
