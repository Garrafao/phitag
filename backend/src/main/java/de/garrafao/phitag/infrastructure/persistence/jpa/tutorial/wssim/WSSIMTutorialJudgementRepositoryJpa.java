package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.wssim;

import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WSSIMTutorialJudgementRepositoryJpa extends JpaRepository<WSSIMTutorialJudgement, WSSIMTutorialJudgementId>, JpaSpecificationExecutor<WSSIMTutorialJudgement> {
    
}
