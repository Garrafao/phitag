package de.garrafao.phitag.infrastructure.persistence.jpa.language;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.language.Language;

public interface LanguageRepositoryJpa extends JpaRepository<Language, Integer> {
    
    Optional<Language> findByName(String name);

}
