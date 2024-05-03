package de.garrafao.phitag.computationalannotator.usepair.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.usepairinstance.data.UsePairInstanceIdDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class UsePairComputationalAnnotatotInstanceDto implements IInstanceDto {

    private final UsePairInstanceIdDto id;

    private final UsageDto firstusage;
    private final UsageDto secondusage;

    private final List<String> labelSet;
    private final String nonLabel;

    private UsePairComputationalAnnotatotInstanceDto(
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

    @Override
    public String toString() {
        return "UsePairComputationalAnnotatotInstanceDto{" +
                "id=" + id +
                ", firstusage=" + firstusage +
                ", secondusage=" + secondusage +
                ", labelSet=" + labelSet +
                ", nonLabel='" + nonLabel + '\'' +
                '}';
    }

    public static UsePairComputationalAnnotatotInstanceDto from(final UsePairInstance usePairInstance) {
        if (usePairInstance == null) {
            return null;
        }

        return new UsePairComputationalAnnotatotInstanceDto(
                UsePairInstanceIdDto.from(usePairInstance.getId()),
                UsageDto.from(usePairInstance.getFirstusage()),
                UsageDto.from(usePairInstance.getSecondusage()),
                usePairInstance.getLabelSet(),
                usePairInstance.getNonLabel());
    }
}
