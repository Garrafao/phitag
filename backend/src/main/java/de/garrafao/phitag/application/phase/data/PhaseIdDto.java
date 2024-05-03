package de.garrafao.phitag.application.phase.data;

import de.garrafao.phitag.domain.phase.PhaseId;
import lombok.Getter;

@Getter
public class PhaseIdDto {
    
        private final String phase;
        private final String project;
        private final String owner;
    
        private PhaseIdDto(final String phase, final String project, final String owner) {
            this.phase = phase;
            this.project = project;
            this.owner = owner;
        }
    
        public static PhaseIdDto from(final PhaseId phaseId) {
            return new PhaseIdDto(phaseId.getName(), phaseId.getProjectid().getName(), phaseId.getProjectid().getOwnername());
        }
    
}
