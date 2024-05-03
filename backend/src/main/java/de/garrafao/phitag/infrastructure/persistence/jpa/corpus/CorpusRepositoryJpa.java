package de.garrafao.phitag.infrastructure.persistence.jpa.corpus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.corpus.Corpus;

public interface CorpusRepositoryJpa extends JpaRepository<Corpus, String>, JpaSpecificationExecutor<Corpus> {
    
}
