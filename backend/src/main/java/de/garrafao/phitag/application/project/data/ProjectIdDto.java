package de.garrafao.phitag.application.project.data;

import de.garrafao.phitag.domain.project.ProjectId;
import lombok.Getter;

@Getter
public class ProjectIdDto {

    private final String name;
    private final String owner;

    private ProjectIdDto(final String name, final String owner) {
        this.name = name;
        this.owner = owner;
    }

    public static ProjectIdDto from(final ProjectId projectId) {
        return new ProjectIdDto(projectId.getName(), projectId.getOwnername());
    }

    
}
