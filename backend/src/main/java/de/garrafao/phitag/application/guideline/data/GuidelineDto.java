package de.garrafao.phitag.application.guideline.data;

import de.garrafao.phitag.domain.guideline.Guideline;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class GuidelineDto {

    private final GuidelineIdDto id;
    private final String content;

    private GuidelineDto(
            final GuidelineIdDto id,
            final String content) {
        this.id = id;
        this.content = content;
    }

    public static GuidelineDto from(@NonNull final Guideline guideline) {
        return new GuidelineDto(
                GuidelineIdDto.from(guideline.getId()),
                guideline.getContent());
    }

}
