package de.garrafao.phitag.application.instance.sentimentandchoice.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceAnnotationId;
import lombok.Getter;

@Getter
public class SentimentAndChoiceInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private SentimentAndChoiceInstanceIdDto(final String instanceId, final String phase, final String project,
                                            final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static SentimentAndChoiceInstanceIdDto from(final SentimentAndChoiceAnnotationId sentimentInstanceId) {
        return new SentimentAndChoiceInstanceIdDto(
                sentimentInstanceId.getInstanceid(),
                sentimentInstanceId.getPhaseid().getName(),
                sentimentInstanceId.getPhaseid().getProjectid().getName(),
                sentimentInstanceId.getPhaseid().getProjectid().getOwnername());
    }
    
}
