package de.garrafao.phitag.application.judgement.userankjudgement.data;

import de.garrafao.phitag.application.instance.userankinstance.data.UseRankInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UseRankJudgementDto implements IJudgementDto {

    private final UseRankJudgementIdDto id;

    private final UseRankInstanceDto instance;

    private final String label;
    private final String comment;

    private UseRankJudgementDto(
            final UseRankJudgementIdDto id,
            final UseRankInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UseRankJudgementDto from(@NonNull final UseRankJudgement useRankJudgement) {
        return new UseRankJudgementDto(
                UseRankJudgementIdDto.from(useRankJudgement.getId()),
                UseRankInstanceDto.from(useRankJudgement.getUseRankInstance()),
                useRankJudgement.getLabel(),
                useRankJudgement.getComment());
    }
}
