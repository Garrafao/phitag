package de.garrafao.phitag.infrastructure.persistence.jpa.entitlement;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.entitlement.EntitlementRepository;

@Repository
public class EntitlementRepositoryBridge implements EntitlementRepository {

    private final EntitlementRepositoryJpa entitlementRepositoryJpa;

    @Autowired
    public EntitlementRepositoryBridge(EntitlementRepositoryJpa entitlementRepositoryJpa) {
        this.entitlementRepositoryJpa = entitlementRepositoryJpa;
    }

    @Override
    public Optional<Entitlement> findByName(String name) {
        return entitlementRepositoryJpa.findByName(name);
    }

    @Override
    public Set<Entitlement> findAll() {
        return entitlementRepositoryJpa.findAll().stream().collect(Collectors.toSet());
    }

}
