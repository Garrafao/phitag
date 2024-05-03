package de.garrafao.phitag.application.joblisting.data.dto;

import de.garrafao.phitag.domain.joblisting.JoblistingId;
import lombok.Getter;

@Getter
public class JoblistingIdDto {

    private final String name;
    private final String owner;
    private final String project;

    private JoblistingIdDto(final String name, final String owner, final String project) {
        this.name = name;
        this.owner = owner;
        this.project = project;
    }

    public static JoblistingIdDto from(final JoblistingId joblistingId) {
        return new JoblistingIdDto(
                joblistingId.getName(),
                joblistingId.getProjectid().getOwnername(),
                joblistingId.getProjectid().getName());

    }

}
