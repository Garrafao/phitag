package de.garrafao.phitag.application.instance.userankrelative.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstanceId;
import lombok.Getter;

@Getter
public class UseRankRelativeInstanceIdDto implements IInstanceIdDto {

    private final String instanceId;
    private final String phase;
    private final String project;
    private final String owner;

    private UseRankRelativeInstanceIdDto(final String instanceId, final String phase, final String project,
                                         final String owner) {
        this.instanceId = instanceId;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static UseRankRelativeInstanceIdDto from(final UseRankRelativeInstanceId useRankRelativeInstanceId) {
        return new UseRankRelativeInstanceIdDto(
                useRankRelativeInstanceId.getInstanceid(),
                useRankRelativeInstanceId.getPhaseid().getName(),
                useRankRelativeInstanceId.getPhaseid().getProjectid().getName(),
                useRankRelativeInstanceId.getPhaseid().getProjectid().getOwnername());
    }

}
