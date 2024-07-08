package de.garrafao.phitag.application.instance.spaninstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.spaninstance.SpanInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class SpanInstanceDto implements IInstanceDto {

    private final SpanInstanceIdDto id;

    private final UsageDto usage;

    private final List<String> labelSet;
    private final String nonLabel;

    private SpanInstanceDto(
            final SpanInstanceIdDto id,
            final UsageDto usage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static SpanInstanceDto from(final SpanInstance spanInstance) {
        if (spanInstance == null) {
            return null;
        }

        return new SpanInstanceDto(
                SpanInstanceIdDto.from(spanInstance.getId()),
                UsageDto.from(spanInstance.getUsage()),
                spanInstance.getLabelSet(),
                spanInstance.getNonLabel());
    }
    
}
