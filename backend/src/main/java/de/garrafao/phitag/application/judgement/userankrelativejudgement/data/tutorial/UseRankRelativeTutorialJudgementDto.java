package de.garrafao.phitag.application.judgement.userankrelativejudgement.data.tutorial;

import de.garrafao.phitag.application.instance.userankrelative.data.UseRankRelativeInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankRelativeTutorialJudgementDto implements IJudgementDto {

    private final UseRankRelativeTutorialJudgementIdDto id;

    private final UseRankRelativeInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankRelativeTutorialJudgementDto(
            final UseRankRelativeTutorialJudgementIdDto id,
            final UseRankRelativeInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankRelativeTutorialJudgementDto from(@NonNull final UseRankRelativeTutorialJudgement useRankJudgement) {
        return new UseRankRelativeTutorialJudgementDto(
                UseRankRelativeTutorialJudgementIdDto.from(useRankJudgement.getId()),
                UseRankRelativeInstanceDto.from(useRankJudgement.getUseRankRelativeInstance()),
                useRankJudgement.getLabel(),
                useRankJudgement.getComment());
    }
}
