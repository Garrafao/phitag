package de.garrafao.phitag.domain.role;

import java.util.Optional;
import java.util.Set;

public interface RoleRepository {

    Set<Role> findAll();

    Optional<Role> findByName(String name);

}