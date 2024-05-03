package de.garrafao.phitag.application.statistics.deprecated.data;

import de.garrafao.phitag.application.phase.data.PhaseDto;
import lombok.Getter;

@Getter
public class PhaseEntryDto {

    private final PhaseDto phase;
    private final int numberOfAnnotations;

    private PhaseEntryDto(PhaseDto phase, int numberOfAnnotations) {
        this.phase = phase;
        this.numberOfAnnotations = numberOfAnnotations;
    }

    public static PhaseEntryDto of(PhaseDto phase, int numberOfAnnotations) {
        return new PhaseEntryDto(phase, numberOfAnnotations);
    }
    
}
