package de.garrafao.phitag.application.judgement.userankjudgement.data.tutorial;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgementId;
import lombok.Getter;

@Getter
public class UseRankTutorialJudgementIdDto implements IJudgementIdDto {

    private final String id;
    private final String instanceId;
    private final String annotator;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankTutorialJudgementIdDto(
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

    public static UseRankTutorialJudgementIdDto from(final UseRankTutorialJudgementId useRankJudgementId) {
        return new UseRankTutorialJudgementIdDto(
                useRankJudgementId.getUUID(),
                useRankJudgementId.getInstanceid().getInstanceid(),
                useRankJudgementId.getAnnotatorid().getUsername(),
                useRankJudgementId.getInstanceid().getPhaseid().getName(),
                useRankJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                useRankJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
