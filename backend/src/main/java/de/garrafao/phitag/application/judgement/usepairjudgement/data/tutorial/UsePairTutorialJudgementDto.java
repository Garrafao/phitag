package de.garrafao.phitag.application.judgement.usepairjudgement.data.tutorial;

import de.garrafao.phitag.application.instance.usepairinstance.data.UsePairInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UsePairTutorialJudgementDto implements IJudgementDto {

    private final UsePairTutorialJudgementIdDto id;

    private final UsePairInstanceDto instance;

    private final String label;
    private final String comment;

    private UsePairTutorialJudgementDto(
            final UsePairTutorialJudgementIdDto id,
            final UsePairInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UsePairTutorialJudgementDto from(@NonNull final UsePairTutorialJudgement usePairTutorialJudgement) {
        return new UsePairTutorialJudgementDto(
                UsePairTutorialJudgementIdDto.from(usePairTutorialJudgement.getId()),
                UsePairInstanceDto.from(usePairTutorialJudgement.getUsePairInstance()),
                usePairTutorialJudgement.getLabel(),
                usePairTutorialJudgement.getComment());
    }
}
