package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.spanjudgement;

import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgement;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpanJudgementRepositoryJpa extends JpaRepository<SpanJudgement, SpanJudgementId>, JpaSpecificationExecutor<SpanJudgement> {
    
}
