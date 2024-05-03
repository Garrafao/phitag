package de.garrafao.phitag.application.instance.lexsubinstance.data;

import java.util.List;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;
import lombok.Getter;

@Getter
public class LexSubInstanceDto implements IInstanceDto {

    private final LexSubInstanceIdDto id;

    private final UsageDto usage;

    private final List<String> labelSet;
    private final String nonLabel;

    private LexSubInstanceDto(
            final LexSubInstanceIdDto id,
            final UsageDto usage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static LexSubInstanceDto from(final LexSubInstance lexSubInstance) {
        if (lexSubInstance == null) {
            return null;
        }

        return new LexSubInstanceDto(
                LexSubInstanceIdDto.from(lexSubInstance.getId()),
                UsageDto.from(lexSubInstance.getUsage()),
                lexSubInstance.getLabelSet(),
                lexSubInstance.getNonLabel());
    }
    
}
