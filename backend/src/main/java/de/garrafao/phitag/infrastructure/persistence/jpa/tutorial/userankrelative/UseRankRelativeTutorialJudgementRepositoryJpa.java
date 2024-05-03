package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankrelative;

import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UseRankRelativeTutorialJudgementRepositoryJpa
        extends JpaRepository<UseRankRelativeTutorialJudgement, UseRankRelativeTutorialJudgementId>, JpaSpecificationExecutor<UseRankRelativeTutorialJudgement> {

}
