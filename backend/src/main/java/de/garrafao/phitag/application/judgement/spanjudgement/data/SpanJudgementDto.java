package de.garrafao.phitag.application.judgement.spanjudgement.data;

import de.garrafao.phitag.application.instance.spaninstance.data.SpanInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class SpanJudgementDto implements IJudgementDto {

    private final SpanJudgementIdDto id;

    private final SpanInstanceDto instance;

    private final String label;
    private final String comment;

    private SpanJudgementDto(
            final SpanJudgementIdDto id,
            final SpanInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static SpanJudgementDto from(@NonNull final SpanJudgement judgement) {
        return new SpanJudgementDto(
                SpanJudgementIdDto.from(judgement.getId()),
                SpanInstanceDto.from(judgement.getInstance()),
                judgement.getLabel(),
                judgement.getComment());
    }
}
