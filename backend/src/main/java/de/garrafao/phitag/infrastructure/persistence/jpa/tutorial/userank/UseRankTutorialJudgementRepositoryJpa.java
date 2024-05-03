package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userank;

import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankTutorialJudgementRepositoryJpa
        extends JpaRepository<UseRankTutorialJudgement, UseRankTutorialJudgementId>, JpaSpecificationExecutor<UseRankTutorialJudgement> {

}
