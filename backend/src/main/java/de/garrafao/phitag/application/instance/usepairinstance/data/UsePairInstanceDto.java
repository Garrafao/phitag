package de.garrafao.phitag.application.instance.usepairinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class UsePairInstanceDto implements IInstanceDto {

    private final UsePairInstanceIdDto id;

    private final UsageDto firstusage;
    private final UsageDto secondusage;

    private final List<String> labelSet;
    private final String nonLabel;

    public UsePairInstanceDto(
            final UsePairInstanceIdDto id,
            final UsageDto firstusage,
            final UsageDto secondusage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static UsePairInstanceDto from(final UsePairInstance usePairInstance) {
        if (usePairInstance == null) {
            return null;
        }

        return new UsePairInstanceDto(
                UsePairInstanceIdDto.from(usePairInstance.getId()),
                UsageDto.from(usePairInstance.getFirstusage()),
                UsageDto.from(usePairInstance.getSecondusage()),
                usePairInstance.getLabelSet(),
                usePairInstance.getNonLabel());
    }
}
