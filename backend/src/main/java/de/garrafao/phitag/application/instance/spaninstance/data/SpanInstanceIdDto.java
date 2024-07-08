package de.garrafao.phitag.application.instance.spaninstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstanceId;
import lombok.Getter;

@Getter
public class SpanInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private SpanInstanceIdDto(final String instanceId, final String phase, final String project,
                              final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static SpanInstanceIdDto from(final SpanInstanceId spanInstanceId) {
        return new SpanInstanceIdDto(
                spanInstanceId.getInstanceid(),
                spanInstanceId.getPhaseid().getName(),
                spanInstanceId.getPhaseid().getProjectid().getName(),
                spanInstanceId.getPhaseid().getProjectid().getOwnername());
    }
    
}
