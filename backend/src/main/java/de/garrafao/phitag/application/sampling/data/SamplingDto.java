package de.garrafao.phitag.application.sampling.data;

import de.garrafao.phitag.domain.sampling.Sampling;
import lombok.Getter;

@Getter
public class SamplingDto {
    private final String name;
    private final String visiblename;

    private SamplingDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static SamplingDto from(Sampling sampling) {
        return new SamplingDto(sampling.getName(), sampling.getVisiblename());
    }
}