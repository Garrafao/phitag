package de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;
import de.garrafao.phitag.infrastructure.persistence.jpa.corpuslexicon.query.CorpusLexiconJpa;

public interface CorpusLexiconRepositoryJpa
        extends JpaRepository<CorpusLexicon, String>, JpaSpecificationExecutor<CorpusLexicon> {

    public Optional<CorpusLexicon> findById(final String id);

    public <T> List<T> findBy(final CorpusLexiconJpa corpusLexiconJpa, final Class<T> type);
}
