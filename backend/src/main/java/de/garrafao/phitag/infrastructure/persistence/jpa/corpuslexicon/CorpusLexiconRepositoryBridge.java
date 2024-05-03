package de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;
import de.garrafao.phitag.domain.corpuslexicon.CorpusLexiconRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon.query.CorpusLexiconJpa;

@Repository
public class CorpusLexiconRepositoryBridge implements CorpusLexiconRepository {

    private final CorpusLexiconRepositoryJpa corpusLexiconRepositoryJpa;

    @Autowired
    public CorpusLexiconRepositoryBridge(CorpusLexiconRepositoryJpa corpusLexiconRepositoryJpa) {
        this.corpusLexiconRepositoryJpa = corpusLexiconRepositoryJpa;
    }

    @Override
    public Optional<CorpusLexicon> findById(String id) {
        return corpusLexiconRepositoryJpa.findById(id);
    }

    @Override
    public List<CorpusLexicon> findByQuery(Query query) {
        CorpusLexiconJpa corpusLexiconJpa = new CorpusLexiconJpa(query);

        return corpusLexiconRepositoryJpa.findAll(corpusLexiconJpa);
    }

    @Override
    public Page<CorpusLexicon> findByQueryPaged(Query query, PageRequestWraper page) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
