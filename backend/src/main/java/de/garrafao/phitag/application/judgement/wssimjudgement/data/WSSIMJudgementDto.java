package de.garrafao.phitag.application.judgement.wssimjudgement.data;

import de.garrafao.phitag.application.instance.wssiminstance.data.WSSIMInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;
import lombok.Getter;

@Getter
public class WSSIMJudgementDto implements IJudgementDto {

    private final WSSIMJudgementIdDto id;

    private final WSSIMInstanceDto instance;

    private final String label;
    private final String comment;

    private WSSIMJudgementDto(
            final WSSIMJudgementIdDto id,
            final WSSIMInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static WSSIMJudgementDto from(final WSSIMJudgement wssimJudgement) {
        return new WSSIMJudgementDto(
                WSSIMJudgementIdDto.from(wssimJudgement.getId()),
                WSSIMInstanceDto.from(wssimJudgement.getWSSIMInstance()),
                wssimJudgement.getLabel(),
                wssimJudgement.getComment());
    }
}
