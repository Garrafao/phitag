package de.garrafao.phitag.infrastructure.persistence.jpa.tutorialannotationmeasurehistory;

import de.garrafao.phitag.domain.phase.PhaseId;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutorialAnnotationMeasureHistoryRepositoryJpa
        extends JpaRepository<TutorialAnnotationMeasureHistory, TutorialAnnotationMeasureHistoryId> {

    List<TutorialAnnotationMeasureHistory> findByIdPhaseid(final PhaseId phaseId);

}
