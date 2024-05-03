package de.garrafao.phitag.domain.visibility;

import java.util.Optional;
import java.util.Set;

public interface VisibilityRepository {
 
    Set<Visibility> findAll();

    Optional<Visibility> findByName(String name);

}
