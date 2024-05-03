package de.garrafao.phitag.application.joblisting.data.dto;

import de.garrafao.phitag.domain.joblisting.Joblisting;
import lombok.Getter;

@Getter
public class JoblistingDto {

    private final JoblistingIdDto id;

    private final String displayname;

    private final boolean open;
    private final String description;

    private JoblistingDto(final JoblistingIdDto id, final String displayname, final boolean open, final String description) {
        this.id = id;

        this.displayname = displayname;
        this.open = open;
        this.description = description;
    }

    public static JoblistingDto from(final Joblisting joblisting) {
        return new JoblistingDto(
                JoblistingIdDto.from(joblisting.getId()),
                joblisting.getDisplayname(),
                joblisting.isOpen(),
                joblisting.getDescription());
    }

}
