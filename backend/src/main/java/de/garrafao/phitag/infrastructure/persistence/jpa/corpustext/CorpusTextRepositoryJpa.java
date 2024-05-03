package de.garrafao.phitag.infrastructure.persistence.jpa.corpustext;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.corpustext.CorpusText;

public interface CorpusTextRepositoryJpa
        extends JpaRepository<CorpusText, String>, JpaSpecificationExecutor<CorpusText> {

            List<CorpusText> findByIdIn(List<String> ids);

}
