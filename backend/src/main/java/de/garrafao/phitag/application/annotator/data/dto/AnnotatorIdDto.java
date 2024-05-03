package de.garrafao.phitag.application.annotator.data.dto;

import de.garrafao.phitag.domain.annotator.AnnotatorId;
import lombok.Getter;

@Getter
public class AnnotatorIdDto {

    private final String user;
    private final String project;
    private final String owner;

    private AnnotatorIdDto(final String user, final String project, final String owner) {
        this.user = user;
        this.project = project;
        this.owner = owner;
    }

    public static AnnotatorIdDto from(final AnnotatorId annotatorId) {
        return new AnnotatorIdDto(
                annotatorId.getUsername(),
                annotatorId.getProjectid().getName(),
                annotatorId.getProjectid().getOwnername());
    }

    
}
