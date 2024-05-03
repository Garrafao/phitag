package de.garrafao.phitag.application.judgement.usepairjudgement.data;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgementId;
import lombok.Getter;

@Getter
public class UsePairJudgementIdDto implements IJudgementIdDto {

    private final String id;
    private final String instanceId;
    private final String annotator;
    private final String phase;
    private final String project;
    private final String owner;

    private UsePairJudgementIdDto(
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

    public static UsePairJudgementIdDto from(final UsePairJudgementId usePairJudgementId) {
        return new UsePairJudgementIdDto(
                usePairJudgementId.getUUID(),
                usePairJudgementId.getInstanceid().getInstanceid(),
                usePairJudgementId.getAnnotatorid().getUsername(),
                usePairJudgementId.getInstanceid().getPhaseid().getName(),
                usePairJudgementId.getInstanceid().getPhaseid().getProjectid().getName(),
                usePairJudgementId.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
