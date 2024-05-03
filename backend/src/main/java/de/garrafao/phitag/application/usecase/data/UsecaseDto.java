package de.garrafao.phitag.application.usecase.data;

import de.garrafao.phitag.domain.usecase.Usecase;
import lombok.Getter;

@Getter
public class UsecaseDto {
    

    private final String name;
    private final String visiblename;

    private UsecaseDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static UsecaseDto from(final Usecase usecase) {
        return new UsecaseDto(usecase.getName(), usecase.getVisiblename());
    }

}
