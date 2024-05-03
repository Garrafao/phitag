package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.example;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleId;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleRepository;

@Repository
public class DictionaryEntrySenseExampleRepositoryBridge implements DictionaryEntrySenseExampleRepository {

    private final DictionaryEntrySenseExampleRepositoryJpa repository;

    @Autowired
    public DictionaryEntrySenseExampleRepositoryBridge(DictionaryEntrySenseExampleRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public DictionaryEntrySenseExample save(DictionaryEntrySenseExample sense) {
        return repository.save(sense);
    }

    @Override
    public void saveAll(Iterable<DictionaryEntrySenseExample> dictionaryEntrySenseExamples) {
        repository.saveAll(dictionaryEntrySenseExamples);
    }

    @Override
    public void delete(DictionaryEntrySenseExample sense) {
        repository.delete(sense);
    }

    @Override
    public Optional<DictionaryEntrySenseExample> findById(DictionaryEntrySenseExampleId id) {
        return repository.findById(id);
    }

}
