package de.garrafao.phitag.application.judgement.userankrelativejudgement.data;

import de.garrafao.phitag.application.instance.userankinstance.data.UseRankInstanceDto;
import de.garrafao.phitag.application.instance.userankrelative.data.UseRankRelativeInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankRelativeJudgementDto implements IJudgementDto {

    private final UseRankRelativeJudgementIdDto id;

    private final UseRankRelativeInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankRelativeJudgementDto(
            final UseRankRelativeJudgementIdDto id,
            final UseRankRelativeInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankRelativeJudgementDto from(@NonNull final UseRankRelativeJudgement useRankRelativeJudgement) {
        return new UseRankRelativeJudgementDto(
                UseRankRelativeJudgementIdDto.from(useRankRelativeJudgement.getId()),
                UseRankRelativeInstanceDto.from(useRankRelativeJudgement.getUseRankRelativeInstance()),
                useRankRelativeJudgement.getLabel(),
                useRankRelativeJudgement.getComment());
    }
}
