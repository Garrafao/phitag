package de.garrafao.phitag.application.judgement.wssimjudgement.data.tutorial;

import de.garrafao.phitag.application.instance.wssiminstance.data.WSSIMInstanceDto;
import de.garrafao.phitag.application.judgement.data.IJudgementDto;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgement;
import lombok.Getter;

@Getter
public class WSSIMTutorialJudgementDto implements IJudgementDto {

    private final WSSIMTutorialJudgementIdDto id;

    private final WSSIMInstanceDto instance;

    private final String label;
    private final String comment;

    private WSSIMTutorialJudgementDto(
            final WSSIMTutorialJudgementIdDto id,
            final WSSIMInstanceDto instance,
            final String label,
            final String comment) {
        this.id = id;
        this.instance = instance;
        this.label = label;
        this.comment = comment;
    }

    public static WSSIMTutorialJudgementDto from(final WSSIMTutorialJudgement wssimJudgement) {
        return new WSSIMTutorialJudgementDto(
                WSSIMTutorialJudgementIdDto.from(wssimJudgement.getId()),
                WSSIMInstanceDto.from(wssimJudgement.getWSSIMInstance()),
                wssimJudgement.getLabel(),
                wssimJudgement.getComment());
    }
}
