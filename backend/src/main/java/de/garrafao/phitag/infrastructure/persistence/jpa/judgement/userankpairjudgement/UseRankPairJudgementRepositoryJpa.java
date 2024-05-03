package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankpairjudgement;

import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgement;
import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankPairJudgementRepositoryJpa
        extends JpaRepository<UseRankPairJudgement, UseRankPairJudgementId>, JpaSpecificationExecutor<UseRankPairJudgement> {

}
