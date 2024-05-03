package de.garrafao.phitag.application.status.data;

import de.garrafao.phitag.domain.status.Status;
import lombok.Getter;

@Getter
public class StatusDto {
    
    private final String name;
    private final String visiblename;

    private StatusDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static StatusDto from(final Status status) {
        return new StatusDto(status.getName(), status.getVisiblename());
    }
    
}
