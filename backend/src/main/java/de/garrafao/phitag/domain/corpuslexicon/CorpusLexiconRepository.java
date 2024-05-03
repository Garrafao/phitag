package de.garrafao.phitag.domain.corpuslexicon;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface CorpusLexiconRepository {

    Optional<CorpusLexicon> findById(String id);

    List<CorpusLexicon> findByQuery(final Query query);

    Page<CorpusLexicon> findByQueryPaged(final Query query, final PageRequestWraper page);

}
