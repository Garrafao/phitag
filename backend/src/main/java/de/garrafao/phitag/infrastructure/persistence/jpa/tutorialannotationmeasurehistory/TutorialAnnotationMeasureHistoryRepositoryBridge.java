package de.garrafao.phitag.infrastructure.persistence.jpa.tutorialannotationmeasurehistory;

import de.garrafao.phitag.domain.phase.PhaseId;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TutorialAnnotationMeasureHistoryRepositoryBridge implements TutorialAnnotationMeasureHistoryRepository {

    private final TutorialAnnotationMeasureHistoryRepositoryJpa tutorialAnnotationMeasureHistoryRepositoryJpa;

    @Autowired
    public TutorialAnnotationMeasureHistoryRepositoryBridge(
            TutorialAnnotationMeasureHistoryRepositoryJpa tutorialAnnotationMeasureHistoryRepositoryJpa) {
        this.tutorialAnnotationMeasureHistoryRepositoryJpa = tutorialAnnotationMeasureHistoryRepositoryJpa;
    }

    @Override
    public List<TutorialAnnotationMeasureHistory> findByIdPhaseid(PhaseId phaseId) {
        return tutorialAnnotationMeasureHistoryRepositoryJpa.findByIdPhaseid(phaseId);
    }



    @Override
    public void save(TutorialAnnotationMeasureHistory tutorialAnnotationMeasureHistory) {
        tutorialAnnotationMeasureHistoryRepositoryJpa.save(tutorialAnnotationMeasureHistory);
    }
    
}
