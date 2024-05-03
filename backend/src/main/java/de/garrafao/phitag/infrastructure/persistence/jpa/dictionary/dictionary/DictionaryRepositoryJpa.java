package de.garrafao.phitag.infrastructure.persistence.jpa.dictionary.dictionary;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryId;

public interface DictionaryRepositoryJpa
        extends JpaRepository<Dictionary, DictionaryId>, JpaSpecificationExecutor<Dictionary> {

    Page<Dictionary> findAllByIdDname(String name, Pageable pageable);

    Page<Dictionary> findAllByIdUname(String uname, Pageable pageable);

}
