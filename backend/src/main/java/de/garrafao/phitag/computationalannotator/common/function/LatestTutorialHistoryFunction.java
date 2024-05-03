package de.garrafao.phitag.computationalannotator.common.function;

import de.garrafao.phitag.application.phase.data.TutorialHistoryDto;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LatestTutorialHistoryFunction {

    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    public LatestTutorialHistoryFunction(TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository) {
        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;
    }

    public TutorialHistoryDto getLatestTutorialHistory(List<TutorialHistoryDto> tutorialHistoryDtos) {
        TutorialHistoryDto latestTutorialHistory = null;

        for (TutorialHistoryDto tutorialHistoryDto : tutorialHistoryDtos) {
            if (latestTutorialHistory == null || tutorialHistoryDto.getTimestamp().compareTo(latestTutorialHistory.getTimestamp()) > 0) {
                latestTutorialHistory = tutorialHistoryDto;
            }
        }

        return latestTutorialHistory;
    }

    public List<TutorialHistoryDto> getTutorialMeasureHistory(final Phase phase) {

        return this.tutorialAnnotationMeasureHistoryRepository.findByIdPhaseid(phase.getId()).stream()
                .map(TutorialHistoryDto::from).toList();
    }

}
