package de.garrafao.phitag.application.project.data;

import de.garrafao.phitag.application.language.data.LanguageDto;
import de.garrafao.phitag.application.visibility.data.VisibilityDto;
import de.garrafao.phitag.domain.project.Project;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class ProjectDto {

    private final ProjectIdDto id;

    private final String displayname;

    private final Boolean active;
    private final VisibilityDto visibility;

    private final LanguageDto language;
    private final String description;

    private ProjectDto(
            final ProjectIdDto id,
            final String displayname,
            final Boolean active,
            final VisibilityDto visibility,
            final LanguageDto language,
            final String description) {
        this.id = id;

        this.displayname = displayname;

        this.active = active;
        this.visibility = visibility;

        this.language = language;
        this.description = description;
    }

    public static ProjectDto from(@NonNull Project project) {
        return new ProjectDto(
                ProjectIdDto.from(project.getId()),
                project.getDisplayname(),
                project.isActive(),
                VisibilityDto.from(project.getVisibility()),
                LanguageDto.from(project.getLanguage()),
                project.getDescription());
    }

}
