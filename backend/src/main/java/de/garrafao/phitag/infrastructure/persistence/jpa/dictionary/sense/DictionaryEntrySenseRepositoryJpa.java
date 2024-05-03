package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.sense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseId;

public interface DictionaryEntrySenseRepositoryJpa extends JpaRepository<DictionaryEntrySense, DictionaryEntrySenseId>, JpaSpecificationExecutor<DictionaryEntrySense> {
    
}
