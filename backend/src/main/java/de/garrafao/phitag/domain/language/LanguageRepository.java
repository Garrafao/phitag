package de.garrafao.phitag.domain.language;

import java.util.Optional;
import java.util.Set;

public interface LanguageRepository {
    
    Optional<Language> findByName(String name);

    Set<Language> findAll();
}