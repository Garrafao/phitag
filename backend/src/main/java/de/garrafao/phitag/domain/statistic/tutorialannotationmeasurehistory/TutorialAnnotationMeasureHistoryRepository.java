package de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory;

import java.util.List;

import de.garrafao.phitag.domain.phase.PhaseId;

public interface TutorialAnnotationMeasureHistoryRepository {

    // List<TutorialAnnotationMeasureHistory> findByIdPhaseidNameAndIdPhaseidProjectidNameAndIdPhaseidProjectidOwnername(String phasename, String projectname, String ownername);

    List<TutorialAnnotationMeasureHistory> findByIdPhaseid(final PhaseId phaseId);

    void save(TutorialAnnotationMeasureHistory tutorialAnnotationMeasureHistory);
}
