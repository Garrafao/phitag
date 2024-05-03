package de.garrafao.phitag.application.judgement.sentimentandchoice.data;

import de.garrafao.phitag.application.judgement.data.IJudgementIdDto;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgementId;
import lombok.Getter;

@Getter
public class SentimentAndChoiceJudgementIdDto implements IJudgementIdDto {

    private final String id;

    private final String instanceId;
    private final String annotator;
    
    private final String phase;
    private final String project;
    private final String owner;

    private SentimentAndChoiceJudgementIdDto(
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

    public static SentimentAndChoiceJudgementIdDto from(final SentimentAndChoiceJudgementId id) {
        return new SentimentAndChoiceJudgementIdDto(
                id.getUUID(),
                id.getInstanceid().getInstanceid(),
                id.getAnnotatorid().getUsername(),
                id.getInstanceid().getPhaseid().getName(),
                id.getInstanceid().getPhaseid().getProjectid().getName(),
                id.getInstanceid().getPhaseid().getProjectid().getOwnername());
    }

}
