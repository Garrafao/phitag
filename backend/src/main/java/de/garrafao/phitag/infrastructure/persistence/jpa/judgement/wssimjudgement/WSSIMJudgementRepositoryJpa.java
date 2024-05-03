package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgementId;

public interface WSSIMJudgementRepositoryJpa extends JpaRepository<WSSIMJudgement, WSSIMJudgementId>, JpaSpecificationExecutor<WSSIMJudgement> {
    
}
