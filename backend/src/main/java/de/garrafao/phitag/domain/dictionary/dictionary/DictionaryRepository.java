package de.garrafao.phitag.domain.dictionary.dictionary;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictionaryRepository {

    Page<Dictionary> findAllByIdUname(final String uname, final Pageable pagerequest);
    
    Page<Dictionary> findAllByIdDname(final String name, final Pageable pagerequest);

    Optional<Dictionary> findById(final DictionaryId id);

    Dictionary save(Dictionary dictionary);
}
