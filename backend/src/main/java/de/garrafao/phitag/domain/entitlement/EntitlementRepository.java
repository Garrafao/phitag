package de.garrafao.phitag.domain.entitlement;

import java.util.Optional;
import java.util.Set;

public interface EntitlementRepository {
    
    public Optional<Entitlement> findByName(String name);

    public Set<Entitlement> findAll();
}
