package de.garrafao.phitag.application.instance.wssimtag.data;

import de.garrafao.phitag.application.instance.data.IInstanceIdDto;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTagId;
import lombok.Getter;

@Getter
public class WSSIMTagIdDto implements IInstanceIdDto {

    private String tagid;

    private String phase;
    private String project;
    private String owner;

    private WSSIMTagIdDto(final String tagid, final String phase, final String project, final String owner) {
        this.tagid = tagid;
        this.phase = phase;
        this.project = project;
        this.owner = owner;
    }

    public static WSSIMTagIdDto from(final WSSIMTagId wssimTagId) {
        return new WSSIMTagIdDto(
                wssimTagId.getTagid(),
                wssimTagId.getPhaseid().getName(),
                wssimTagId.getPhaseid().getProjectid().getName(),
                wssimTagId.getPhaseid().getProjectid().getOwnername());
    }

    @Override
    public String getInstanceId() {
        return tagid;
    }

}
