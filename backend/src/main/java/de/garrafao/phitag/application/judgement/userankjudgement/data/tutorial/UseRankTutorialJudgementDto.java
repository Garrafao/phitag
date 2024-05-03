package de.garrafao.phitag.application.judgement.userankjudgement.data.tutorial;

import de.garrafao.phitag.application.instance.userankinstance.data.UseRankInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankTutorialJudgementDto implements IJudgementDto {

    private final UseRankTutorialJudgementIdDto id;

    private final UseRankInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankTutorialJudgementDto(
            final UseRankTutorialJudgementIdDto id,
            final UseRankInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankTutorialJudgementDto from(@NonNull final UseRankTutorialJudgement useRankJudgement) {
        return new UseRankTutorialJudgementDto(
                UseRankTutorialJudgementIdDto.from(useRankJudgement.getId()),
                UseRankInstanceDto.from(useRankJudgement.getUseRankInstance()),
                useRankJudgement.getLabel(),
                useRankJudgement.getComment());
    }
}
