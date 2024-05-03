package de.garrafao.phitag.application.judgement.userankrelativejudgement.data;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgementId;
import lombok.Getter;

@Getter
public class UseRankRelativeJudgementIdDto implements IJudgementIdDto {

    private final String id;
    private final String instanceId;
    private final String annotator;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankRelativeJudgementIdDto(
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

    public static UseRankRelativeJudgementIdDto from(final UseRankRelativeJudgementId useRankRelativeJudgementId) {
        return new UseRankRelativeJudgementIdDto(
                useRankRelativeJudgementId.getUUID(),
                useRankRelativeJudgementId.getInstanceid().getInstanceid(),
                useRankRelativeJudgementId.getAnnotatorid().getUsername(),
                useRankRelativeJudgementId.getInstanceid().getPhaseid().getName(),
                useRankRelativeJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                useRankRelativeJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
