package de.garrafao.phitag.domain.dictionary.entry;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import de.garrafao.phitag.domain.core.Query;

public interface DictionaryEntryRepository {

    Page<DictionaryEntry> findAll(PageRequest pageRequest);

    Page<DictionaryEntry> findAllByQuery(Query query, PageRequest pageRequest);

    List<DictionaryEntry> findAllByIdDictionaryidDnameAndIdDictionaryidUname(String dictionaryDname, String dictionaryUname);

    Optional<DictionaryEntry> findById(final DictionaryEntryId id);

    DictionaryEntry save(DictionaryEntry dictionaryEntry);

    void saveAll(Iterable<DictionaryEntry> dictionaryEntries);

    void delete(DictionaryEntry dictionaryEntry);
    
}
