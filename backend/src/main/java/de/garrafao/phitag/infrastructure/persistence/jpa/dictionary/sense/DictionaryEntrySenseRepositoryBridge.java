package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.sense;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseId;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseRepository;

@Repository
public class DictionaryEntrySenseRepositoryBridge implements DictionaryEntrySenseRepository {

    private final DictionaryEntrySenseRepositoryJpa repository;

    @Autowired
    public DictionaryEntrySenseRepositoryBridge(DictionaryEntrySenseRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public DictionaryEntrySense save(DictionaryEntrySense sense) {
        return repository.save(sense);
    }

    @Override
    public void saveAll(Iterable<DictionaryEntrySense> dictionaryEntrySenses) {
        repository.saveAll(dictionaryEntrySenses);
    }

    @Override
    public void delete(DictionaryEntrySense sense) {
        repository.delete(sense);
    }

    @Override
    public Optional<DictionaryEntrySense> findById(DictionaryEntrySenseId id) {
        return repository.findById(id);
    }

}
