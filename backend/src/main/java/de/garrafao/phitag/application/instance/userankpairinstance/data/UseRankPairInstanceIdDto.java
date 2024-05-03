package de.garrafao.phitag.application.instance.userankpairinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstanceId;
import lombok.Getter;

@Getter
public class UseRankPairInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankPairInstanceIdDto(final String instanceId, final String phase, final String project,
                                     final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static UseRankPairInstanceIdDto from(final UseRankPairInstanceId useRankPairInstanceId) {
        return new UseRankPairInstanceIdDto(
                useRankPairInstanceId.getInstanceid(),
                useRankPairInstanceId.getPhaseid().getName(),
                useRankPairInstanceId.getPhaseid().getProjectid().getName(),
                useRankPairInstanceId.getPhaseid().getProjectid().getOwnername());
    }

}
