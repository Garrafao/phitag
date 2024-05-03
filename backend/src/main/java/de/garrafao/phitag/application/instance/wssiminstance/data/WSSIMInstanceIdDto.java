package de.garrafao.phitag.application.instance.wssiminstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstanceId;
import lombok.Getter;

@Getter
public class WSSIMInstanceIdDto implements IInstanceIdDto {

    private final String instanceid;

    private final String phase;
    private final String project;
    private final String owner;

    private WSSIMInstanceIdDto(final String instanceid, final String phase, final String project, final String owner) {
        this.instanceid = instanceid;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static WSSIMInstanceIdDto from(final WSSIMInstanceId wssimInstanceId) {
        return new WSSIMInstanceIdDto(
                wssimInstanceId.getInstanceid(),
                wssimInstanceId.getPhaseid().getName(),
                wssimInstanceId.getPhaseid().getProjectid().getName(),
                wssimInstanceId.getPhaseid().getProjectid().getOwnername());
    }

    @Override
    public String getInstanceId() {
        return this.instanceid;
    }

}
