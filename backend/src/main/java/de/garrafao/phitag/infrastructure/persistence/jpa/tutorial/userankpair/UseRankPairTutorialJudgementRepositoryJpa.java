package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankpair;

import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankPairTutorialJudgementRepositoryJpa
        extends JpaRepository<UseRankPairTutorialJudgement, UseRankPairTutorialJudgementId>, JpaSpecificationExecutor<UseRankPairTutorialJudgement> {

}
