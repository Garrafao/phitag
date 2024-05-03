package de.garrafao.phitag.application.instance.usepairinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstanceId;
import lombok.Getter;

@Getter
public class UsePairInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private UsePairInstanceIdDto(final String instanceId, final String phase, final String project,
            final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static UsePairInstanceIdDto from(final UsePairInstanceId usePairInstanceId) {
        return new UsePairInstanceIdDto(
                usePairInstanceId.getInstanceid(),
                usePairInstanceId.getPhaseid().getName(),
                usePairInstanceId.getPhaseid().getProjectid().getName(),
                usePairInstanceId.getPhaseid().getProjectid().getOwnername());
    }

}
