package de.garrafao.phitag.application.instance.lexsubinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstanceId;
import lombok.Getter;

@Getter
public class LexSubInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private LexSubInstanceIdDto(final String instanceId, final String phase, final String project,
            final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static LexSubInstanceIdDto from(final LexSubInstanceId lexSubInstanceId) {
        return new LexSubInstanceIdDto(
                lexSubInstanceId.getInstanceid(),
                lexSubInstanceId.getPhaseid().getName(),
                lexSubInstanceId.getPhaseid().getProjectid().getName(),
                lexSubInstanceId.getPhaseid().getProjectid().getOwnername());
    }
    
}
