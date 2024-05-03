package de.garrafao.phitag.application.judgement.lexsubjudgement.data;

import de.garrafao.phitag.application.instance.lexsubinstance.data.LexSubInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class LexJudgementDto implements IJudgementDto {

    private final LexSubJudgementIdDto id;

    private final LexSubInstanceDto instance;

    private final String label;
    private final String comment;

    private LexJudgementDto(
            final LexSubJudgementIdDto id,
            final LexSubInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static LexJudgementDto from(@NonNull final LexSubJudgement judgement) {
        return new LexJudgementDto(
                LexSubJudgementIdDto.from(judgement.getId()),
                LexSubInstanceDto.from(judgement.getInstance()),
                judgement.getLabel(),
                judgement.getComment());
    }
}
