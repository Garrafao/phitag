package de.garrafao.phitag.application.judgement.lexsubjudgement.data.tutorial;

import de.garrafao.phitag.application.instance.lexsubinstance.data.LexSubInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class LexSubTutorialJudgementDto implements IJudgementDto {

    private final LexSubTutorialJudgementIdDto id;

    private final LexSubInstanceDto instance;

    private final String label;
    private final String comment;

    private LexSubTutorialJudgementDto(
            final LexSubTutorialJudgementIdDto id,
            final LexSubInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static LexSubTutorialJudgementDto from(@NonNull final LexSubTutorialJudgement judgement) {
        return new LexSubTutorialJudgementDto(
                LexSubTutorialJudgementIdDto.from(judgement.getId()),
                LexSubInstanceDto.from(judgement.getInstance()),
                judgement.getLabel(),
                judgement.getComment());
    }
}
