package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleId;

public interface DictionaryEntrySenseExampleRepositoryJpa extends JpaRepository<DictionaryEntrySenseExample, DictionaryEntrySenseExampleId>, JpaSpecificationExecutor<DictionaryEntrySenseExample> { 
    
}
