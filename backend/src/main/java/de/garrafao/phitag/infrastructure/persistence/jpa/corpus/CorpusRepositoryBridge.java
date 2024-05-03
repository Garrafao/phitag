package de.garrafao.phitag.infrastructure.persistence.jpa.corpus;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.corpus.Corpus;
import de.garrafao.phitag.domain.corpus.CorpusRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.corpus.query.CorpusJpa;

@Repository
public class CorpusRepositoryBridge implements CorpusRepository {

    private final CorpusRepositoryJpa corpusRepositoryJpa;

    @Autowired
    public CorpusRepositoryBridge(CorpusRepositoryJpa corpusRepositoryJpa) {
        this.corpusRepositoryJpa = corpusRepositoryJpa;
    }

    @Override
    public Optional<Corpus> findById(String id) {
        return this.corpusRepositoryJpa.findById(id);
    }

    @Override
    public Page<Corpus> findByQueryPaged(Query query, PageRequestWraper pagewraper) {
        CorpusJpa corpusJpa = new CorpusJpa(query);
        return this.corpusRepositoryJpa.findAll(corpusJpa, pagewraper.getPageRequest());
    }


    
}
