package de.garrafao.phitag.infrastructure.persistence.jpa.language;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.language.LanguageRepository;

@Repository
public class LanguageRepositoryBridge implements LanguageRepository {

    private final LanguageRepositoryJpa languageRepositoryJpa;

    @Autowired
    public LanguageRepositoryBridge(LanguageRepositoryJpa languageRepositoryJpa) {
        this.languageRepositoryJpa = languageRepositoryJpa;
    }

    @Override
    public Optional<Language> findByName(String name) {
        return this.languageRepositoryJpa.findByName(name);
    }

    @Override
    public Set<Language> findAll() {
        return new HashSet<>(this.languageRepositoryJpa.findAll());
    }
    
}
