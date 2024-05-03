package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.lexsub;

import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LexSubTutorialJudgementRepositoryJpa extends JpaRepository<LexSubTutorialJudgement, LexSubTutorialJudgementId>, JpaSpecificationExecutor<LexSubTutorialJudgement> {
    
}
