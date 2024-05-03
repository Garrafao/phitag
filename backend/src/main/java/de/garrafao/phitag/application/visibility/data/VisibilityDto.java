package de.garrafao.phitag.application.visibility.data;


import de.garrafao.phitag.domain.visibility.Visibility;
import lombok.Getter;

@Getter
public class VisibilityDto {

    private final String name;
    private final String visiblename;

    private VisibilityDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static VisibilityDto from(final Visibility visibility) {
        return new VisibilityDto(visibility.getName(), visibility.getVisiblename());
    }
    
}
