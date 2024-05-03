package de.garrafao.phitag.domain.corpus;

import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface CorpusRepository {

    Optional<Corpus> findById(String id);

    Page<Corpus> findByQueryPaged(Query query, PageRequestWraper pagewraper);
    
}
