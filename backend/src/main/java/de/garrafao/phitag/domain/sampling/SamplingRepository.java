package de.garrafao.phitag.domain.sampling;

import java.util.List;
import java.util.Optional;

public interface SamplingRepository {

    List<Sampling> findAll();

    Optional<Sampling> findByName(String name);
    
}
