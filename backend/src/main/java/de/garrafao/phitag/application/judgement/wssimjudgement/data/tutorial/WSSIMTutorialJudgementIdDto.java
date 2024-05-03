package de.garrafao.phitag.application.judgement.wssimjudgement.data.tutorial;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgementId;
import lombok.Getter;

@Getter
public class WSSIMTutorialJudgementIdDto implements IJudgementIdDto {

    private final String id;

    private final String instanceId;
    private final String annotator;

    private final String phase;
    private final String project;
    private final String owner;

    private WSSIMTutorialJudgementIdDto(
            final String id,
            final String instanceId, final String annotator,
            final String phase, final String project, final String owner) {
        this.id = id;

        this.instanceId = instanceId;
        this.annotator = annotator;

        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static WSSIMTutorialJudgementIdDto from(final WSSIMTutorialJudgementId wssimJudgementId) {
        return new WSSIMTutorialJudgementIdDto(
                wssimJudgementId.getUUID(),
                wssimJudgementId.getInstanceid().getInstanceid(),
                wssimJudgementId.getAnnotatorid().getUsername(),
                wssimJudgementId.getInstanceid().getPhaseid().getName(),
                wssimJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                wssimJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
