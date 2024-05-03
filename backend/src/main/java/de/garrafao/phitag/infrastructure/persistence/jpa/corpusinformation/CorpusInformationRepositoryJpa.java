package de.garrafao.phitag.infrastructure.persistence.jpa.corpusinformation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import de.garrafao.phitag.domain.corpusinformation.CorpusInformation;

public interface CorpusInformationRepositoryJpa extends JpaRepository<CorpusInformation, String>, JpaSpecificationExecutor<CorpusInformation> {

    @Query("SELECT DISTINCT c.corpusnameshort FROM CorpusInformation c")
    List<String> findDistinctCorpusnameshortBy();
    
}
