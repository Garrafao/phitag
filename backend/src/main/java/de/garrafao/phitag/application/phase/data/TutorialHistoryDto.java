package de.garrafao.phitag.application.phase.data;

import de.garrafao.phitag.application.annotator.data.dto.AnnotatorIdDto;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistory;
import lombok.Getter;

@Getter
public class TutorialHistoryDto {

    // ID
    private final String timestamp;
    private final PhaseIdDto phaseId;
    private final AnnotatorIdDto annotatorId;

    // Data
    private final Double measure;

    // Status
    private final Boolean passed;

    public TutorialHistoryDto(
            final String timestamp,
            final PhaseIdDto phaseId,
            final AnnotatorIdDto annotatorId,
            final Double measure,
            final Boolean passed) {
        this.timestamp = timestamp;
        this.phaseId = phaseId;
        this.annotatorId = annotatorId;
        this.measure = measure;
        this.passed = passed;
    }

    public static TutorialHistoryDto from(final TutorialAnnotationMeasureHistory history) {
        return new TutorialHistoryDto(
                history.getId().getUuid(),
                PhaseIdDto.from(history.getPhase().getId()),
                AnnotatorIdDto.from(history.getAnnotator().getId()),
                history.getMeasure(),
                history.getPassed());
    }

}
