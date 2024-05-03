package de.garrafao.phitag.infrastructure.persistence.jpa.corpustext;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.corpustext.CorpusText;
import de.garrafao.phitag.domain.corpustext.CorpusTextRepository;

@Repository
public class CorpusTextRepositoryBridge implements CorpusTextRepository {
    
    private final CorpusTextRepositoryJpa corpusTextRepositoryJpa;

    @Autowired
    public CorpusTextRepositoryBridge(CorpusTextRepositoryJpa corpusTextRepositoryJpa) {
        this.corpusTextRepositoryJpa = corpusTextRepositoryJpa;
    }

    @Override
    public Optional<CorpusText> findById(String id) {
        return this.corpusTextRepositoryJpa.findById(id);
    }

    @Override
    public List<CorpusText> findByIdIn(List<String> ids) {
        return findByIdIn(ids);
    }
}
