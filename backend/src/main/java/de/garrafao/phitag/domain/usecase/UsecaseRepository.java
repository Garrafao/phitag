package de.garrafao.phitag.domain.usecase;

import java.util.List;
import java.util.Optional;

public interface UsecaseRepository {
    
    List<Usecase> findAll();

    Optional<Usecase> findByName(final String name);
    
}
