package de.garrafao.phitag.application.statistics.deprecated.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.garrafao.phitag.application.annotator.data.dto.AnnotatorDto;
import de.garrafao.phitag.application.phase.data.PhaseDto;
import de.garrafao.phitag.domain.phase.Phase;
import lombok.Getter;

@Getter
public class AnnotatorStatisticDto {
    
    private final AnnotatorDto annotator;
    
    private final int numberOfAnnotations;

    private final List<PhaseEntryDto> numberOfAnnotationsPerPhase;

    private AnnotatorStatisticDto(AnnotatorDto annotator, int numberOfAnnotations, List<PhaseEntryDto> numberOfAnnotationsPerPhase) {
        this.annotator = annotator;
        this.numberOfAnnotations = numberOfAnnotations;
        this.numberOfAnnotationsPerPhase = numberOfAnnotationsPerPhase;
    }

    public static AnnotatorStatisticDto of(AnnotatorDto annotator, int numberOfAnnotations, Map<Phase, Integer> numberOfAnnotationsPerPhase) {
        List<PhaseEntryDto> numberOfAnnotationsPerPhaseDto = new ArrayList<>();
        for (Map.Entry<Phase, Integer> entry : numberOfAnnotationsPerPhase.entrySet()) {
            numberOfAnnotationsPerPhaseDto.add(PhaseEntryDto.of(PhaseDto.from(entry.getKey()), entry.getValue()));
        }
        return new AnnotatorStatisticDto(annotator, numberOfAnnotations, numberOfAnnotationsPerPhaseDto);
    }
}
