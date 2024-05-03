package de.garrafao.phitag.computationalannotator.lexsub.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.lexsubinstance.data.LexSubInstanceIdDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class LexsubComputationalAnnotatotInstanceDto implements IInstanceDto {

    private final LexSubInstanceIdDto id;

    private final UsageDto usage;

    private final List<String> labelSet;
    private final String nonLabel;

    private LexsubComputationalAnnotatotInstanceDto(
            final LexSubInstanceIdDto id,
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
        return "LexsubComputationalAnnotatotInstanceDto{" +
                "id=" + id +
                ", usage=" + usage +
                ", labelSet=" + labelSet +
                ", nonLabel='" + nonLabel + '\'' +
                '}';
    }

    public static LexsubComputationalAnnotatotInstanceDto from(final LexSubInstance lexSubInstance) {
        if (lexSubInstance== null) {
            return null;
        }

        return new LexsubComputationalAnnotatotInstanceDto(
                LexSubInstanceIdDto.from(lexSubInstance.getId()),
                UsageDto.from(lexSubInstance.getUsage()),
                lexSubInstance.getLabelSet(),
                lexSubInstance.getNonLabel());
    }
}
