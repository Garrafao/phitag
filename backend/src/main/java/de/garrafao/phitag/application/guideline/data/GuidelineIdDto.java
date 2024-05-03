package de.garrafao.phitag.application.guideline.data;

import de.garrafao.phitag.domain.guideline.GuidelineId;
import lombok.Getter;

@Getter
public class GuidelineIdDto {

    private final String name;
    private final String project;
    private final String owner;

    private GuidelineIdDto(final String name, final String project, final String owner) {
        this.name = name;
        this.project = project;
        this.owner = owner;
    }

    public static GuidelineIdDto from(final GuidelineId guidelineId) {
        return new GuidelineIdDto(
                guidelineId.getName(),
                guidelineId.getProjectid().getName(),
                guidelineId.getProjectid().getOwnername());
    }

}
