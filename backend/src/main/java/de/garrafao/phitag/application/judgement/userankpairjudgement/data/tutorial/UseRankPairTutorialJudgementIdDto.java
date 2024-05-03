package de.garrafao.phitag.application.judgement.userankpairjudgement.data.tutorial;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgementId;
import lombok.Getter;

@Getter
public class UseRankPairTutorialJudgementIdDto implements IJudgementIdDto {

    private final String id;
    private final String instanceId;
    private final String annotator;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankPairTutorialJudgementIdDto(
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

    public static UseRankPairTutorialJudgementIdDto from(final UseRankPairTutorialJudgementId useRankPairJudgementId) {
        return new UseRankPairTutorialJudgementIdDto(
                useRankPairJudgementId.getUUID(),
                useRankPairJudgementId.getInstanceid().getInstanceid(),
                useRankPairJudgementId.getAnnotatorid().getUsername(),
                useRankPairJudgementId.getInstanceid().getPhaseid().getName(),
                useRankPairJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                useRankPairJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
