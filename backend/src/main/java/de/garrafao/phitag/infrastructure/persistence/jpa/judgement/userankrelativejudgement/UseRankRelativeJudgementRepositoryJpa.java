package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankrelativejudgement;

import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgement;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankRelativeJudgementRepositoryJpa
        extends JpaRepository<UseRankRelativeJudgement, UseRankRelativeJudgementId>, JpaSpecificationExecutor<UseRankRelativeJudgement> {

}
