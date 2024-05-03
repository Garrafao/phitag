package de.garrafao.phitag.application.judgement.usepairjudgement.data.tutorial;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgementId;
import lombok.Getter;

@Getter
public class UsePairTutorialJudgementIdDto implements IJudgementIdDto {

    private final String id;
    private final String instanceId;
    private final String annotator;
    private final String phase;
    private final String project;
    private final String owner;

    private UsePairTutorialJudgementIdDto(
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

    public static UsePairTutorialJudgementIdDto from(final UsePairTutorialJudgementId usePairTutorialJudgementId) {
        return new UsePairTutorialJudgementIdDto(
                usePairTutorialJudgementId.getUUID(),
                usePairTutorialJudgementId.getInstanceid().getInstanceid(),
                usePairTutorialJudgementId.getAnnotatorid().getUsername(),
                usePairTutorialJudgementId.getInstanceid().getPhaseid().getName(),
                usePairTutorialJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                usePairTutorialJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }
}
