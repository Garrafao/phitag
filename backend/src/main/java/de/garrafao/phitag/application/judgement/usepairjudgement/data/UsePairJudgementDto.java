package de.garrafao.phitag.application.judgement.usepairjudgement.data;

import de.garrafao.phitag.application.instance.usepairinstance.data.UsePairInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UsePairJudgementDto implements IJudgementDto {

    private final UsePairJudgementIdDto id;

    private final UsePairInstanceDto instance;

    private final String label;
    private final String comment;

    private UsePairJudgementDto(
            final UsePairJudgementIdDto id,
            final UsePairInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static UsePairJudgementDto from(@NonNull final UsePairJudgement usePairJudgement) {
        return new UsePairJudgementDto(
                UsePairJudgementIdDto.from(usePairJudgement.getId()),
                UsePairInstanceDto.from(usePairJudgement.getUsePairInstance()),
                usePairJudgement.getLabel(),
                usePairJudgement.getComment());
    }
}
