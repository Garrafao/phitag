package de.garrafao.phitag.application.instance.wssiminstance.data;

import java.util.List;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.wssimtag.data.WSSIMTagDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import lombok.Getter;

@Getter
public class WSSIMInstanceDto implements IInstanceDto {

    private final WSSIMInstanceIdDto id;

    private final UsageDto usage;
    private final WSSIMTagDto tag;

    private final List<String> labelSet;
    private final String nonLabel;

    private WSSIMInstanceDto(
            final WSSIMInstanceIdDto id,
            final UsageDto usage,
            final WSSIMTagDto tag,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.usage = usage;
        this.tag = tag;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static WSSIMInstanceDto from(final WSSIMInstance wssimInstance) {
        if (wssimInstance == null) {
            return null;
        }

        return new WSSIMInstanceDto(
                WSSIMInstanceIdDto.from(wssimInstance.getId()),
                UsageDto.from(wssimInstance.getUsage()),
                WSSIMTagDto.from(wssimInstance.getWssimtag()),
                wssimInstance.getLabelSet(),
                wssimInstance.getNonLabel());
    }

}
