package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryId;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry.query.DictionaryEntryQueryJpa;

@Repository
public class DictionaryEntryRepositoryBridge implements DictionaryEntryRepository {

    private final DictionaryEntryRepositoryJpa repository;

    @Autowired
    public DictionaryEntryRepositoryBridge(DictionaryEntryRepositoryJpa repository) {
        this.repository = repository;
    }

    @Override
    public Page<DictionaryEntry> findAll(PageRequest pagerequest) {
        return repository.findAll(pagerequest);
    }

    @Override
    public Page<DictionaryEntry> findAllByQuery(Query query, PageRequest pagerequest) {
        DictionaryEntryQueryJpa queryJpa = new DictionaryEntryQueryJpa(query);

        return repository.findAll(queryJpa, pagerequest);
    }

    @Override
    public DictionaryEntry save(DictionaryEntry dictionaryEntry) {
        return repository.save(dictionaryEntry);
    }

    @Override
    public void saveAll(Iterable<DictionaryEntry> dictionaryEntries) {
        repository.saveAll(dictionaryEntries);
    }

    @Override
    public void delete(DictionaryEntry dictionaryEntry) {
        repository.delete(dictionaryEntry);
    }

    @Override
    public Optional<DictionaryEntry> findById(DictionaryEntryId id) {
        return repository.findById(id);
    }

    @Override
    public List<DictionaryEntry> findAllByIdDictionaryidDnameAndIdDictionaryidUname(String dictionaryDname,
            String dictionaryUname) {
        return repository.findAllByIdDictionaryidDnameAndIdDictionaryidUname(dictionaryDname, dictionaryUname);
    }

}
