package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.lexsubjudgement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgement;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgementId;

public interface LexSubJudgementRepositoryJpa extends JpaRepository<LexSubJudgement, LexSubJudgementId>, JpaSpecificationExecutor<LexSubJudgement> {
    
}
