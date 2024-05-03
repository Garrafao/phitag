package de.garrafao.phitag.application.judgement.userankpairjudgement.data.tutorial;

import de.garrafao.phitag.application.instance.userankpairinstance.data.UseRankPairInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankPairTutorialJudgementDto implements IJudgementDto {

    private final UseRankPairTutorialJudgementIdDto id;

    private final UseRankPairInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankPairTutorialJudgementDto(
            final UseRankPairTutorialJudgementIdDto id,
            final UseRankPairInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankPairTutorialJudgementDto from(@NonNull final UseRankPairTutorialJudgement useRankPairJudgement) {
        return new UseRankPairTutorialJudgementDto(
                UseRankPairTutorialJudgementIdDto.from(useRankPairJudgement.getId()),
                UseRankPairInstanceDto.from(useRankPairJudgement.getUseRankPairInstance()),
                useRankPairJudgement.getLabel(),
                useRankPairJudgement.getComment());
    }
}
