package de.garrafao.phitag.application.instance.userankinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstanceId;
import lombok.Getter;

@Getter
public class UseRankInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankInstanceIdDto(final String instanceId, final String phase, final String project,
                                 final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static UseRankInstanceIdDto from(final UseRankInstanceId useRankInstanceId) {
        return new UseRankInstanceIdDto(
                useRankInstanceId.getInstanceid(),
                useRankInstanceId.getPhaseid().getName(),
                useRankInstanceId.getPhaseid().getProjectid().getName(),
                useRankInstanceId.getPhaseid().getProjectid().getOwnername());
    }

}
