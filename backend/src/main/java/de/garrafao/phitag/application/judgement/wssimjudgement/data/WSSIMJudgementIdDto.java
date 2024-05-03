package de.garrafao.phitag.application.judgement.wssimjudgement.data;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgementId;
import lombok.Getter;

@Getter
public class WSSIMJudgementIdDto implements IJudgementIdDto {

    private final String id;

    private final String instanceId;
    private final String annotator;

    private final String phase;
    private final String project;
    private final String owner;

    private WSSIMJudgementIdDto(
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

    public static WSSIMJudgementIdDto from(final WSSIMJudgementId wssimJudgementId) {
        return new WSSIMJudgementIdDto(
                wssimJudgementId.getUUID(),
                wssimJudgementId.getInstanceid().getInstanceid(),
                wssimJudgementId.getAnnotatorid().getUsername(),
                wssimJudgementId.getInstanceid().getPhaseid().getName(),
                wssimJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                wssimJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
