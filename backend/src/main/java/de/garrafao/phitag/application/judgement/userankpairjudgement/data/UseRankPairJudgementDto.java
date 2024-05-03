package de.garrafao.phitag.application.judgement.userankpairjudgement.data;

import de.garrafao.phitag.application.instance.userankpairinstance.data.UseRankPairInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankPairJudgementDto implements IJudgementDto {

    private final UseRankPairJudgementIdDto id;

    private final UseRankPairInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankPairJudgementDto(
            final UseRankPairJudgementIdDto id,
            final UseRankPairInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankPairJudgementDto from(@NonNull final UseRankPairJudgement useRankPairJudgement) {
        return new UseRankPairJudgementDto(
                UseRankPairJudgementIdDto.from(useRankPairJudgement.getId()),
                UseRankPairInstanceDto.from(useRankPairJudgement.getUseRankPairInstance()),
                useRankPairJudgement.getLabel(),
                useRankPairJudgement.getComment());
    }
}
