package de.garrafao.phitag.infrastructure.persistence.jpa.role;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.role.Role;
import de.garrafao.phitag.domain.role.RoleRepository;

@Repository
public class RoleRepositoryBridge implements RoleRepository {

    private final RoleRepositoryJpa roleRepositoryJpa;

    @Autowired
    public RoleRepositoryBridge(RoleRepositoryJpa roleRepositoryJpa) {
        this.roleRepositoryJpa = roleRepositoryJpa;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return this.roleRepositoryJpa.findByName(name);
    }

    @Override
    public Set<Role> findAll() {
        return new HashSet<>(this.roleRepositoryJpa.findAll());
    }

    
}
