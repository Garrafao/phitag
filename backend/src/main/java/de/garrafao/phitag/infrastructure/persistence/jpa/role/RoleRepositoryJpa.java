package de.garrafao.phitag.infrastructure.persistence.jpa.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.role.Role;

public interface RoleRepositoryJpa extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByName(String name);

}
