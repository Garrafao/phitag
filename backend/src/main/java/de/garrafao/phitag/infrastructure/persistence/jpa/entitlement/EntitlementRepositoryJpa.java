package de.garrafao.phitag.infrastructure.persistence.jpa.entitlement;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.entitlement.Entitlement;

public interface EntitlementRepositoryJpa extends JpaRepository<Entitlement, Integer> {

    Optional<Entitlement> findByName(String name);

}
