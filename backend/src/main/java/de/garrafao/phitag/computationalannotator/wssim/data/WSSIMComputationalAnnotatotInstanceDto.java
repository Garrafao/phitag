package de.garrafao.phitag.computationalannotator.wssim.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.wssiminstance.data.WSSIMInstanceIdDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class WSSIMComputationalAnnotatotInstanceDto implements IInstanceDto {

    private final WSSIMInstanceIdDto id;

    private final UsageDto usage;

    private final List<String> labelSet;
    private final String nonLabel;

    private WSSIMComputationalAnnotatotInstanceDto(
            final WSSIMInstanceIdDto id,
            final UsageDto usage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    @Override
    public String toString() {
        return "WSSIMComputationalAnnotatotInstanceDto{" +
                "id=" + id +
                ", usage=" + usage +
                ", labelSet=" + labelSet +
                ", nonLabel='" + nonLabel + '\'' +
                '}';
    }

    public static WSSIMComputationalAnnotatotInstanceDto from(final WSSIMInstance wssimInstance) {
        if (wssimInstance== null) {
            return null;
        }

        return new WSSIMComputationalAnnotatotInstanceDto(
                WSSIMInstanceIdDto.from(wssimInstance.getId()),
                UsageDto.from(wssimInstance.getUsage()),
                wssimInstance.getLabelSet(),
                wssimInstance.getNonLabel());
    }
}
