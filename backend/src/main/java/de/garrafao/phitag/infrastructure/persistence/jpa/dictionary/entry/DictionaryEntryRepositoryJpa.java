package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.entry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryId;

public interface DictionaryEntryRepositoryJpa extends JpaRepository<DictionaryEntry, DictionaryEntryId>, JpaSpecificationExecutor<DictionaryEntry> {

    List<DictionaryEntry> findAllByIdDictionaryidDnameAndIdDictionaryidUname(String dictionaryDname,
            String dictionaryUname); 

}
