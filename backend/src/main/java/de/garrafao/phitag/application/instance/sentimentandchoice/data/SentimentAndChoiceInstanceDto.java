package de.garrafao.phitag.application.instance.sentimentandchoice.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class SentimentAndChoiceInstanceDto implements IInstanceDto {

    private final SentimentAndChoiceInstanceIdDto id;

    private final UsageDto usage;

    private final List<String> labelSet;
    private final String nonLabel;

    private SentimentAndChoiceInstanceDto(
            final SentimentAndChoiceInstanceIdDto id,
            final UsageDto usage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;

        this.usage = usage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static SentimentAndChoiceInstanceDto from(final SentimentAndChoiceInstance sentimentAndChoiceInstance) {
        if (sentimentAndChoiceInstance == null) {
            return null;
        }

        return new SentimentAndChoiceInstanceDto(
                SentimentAndChoiceInstanceIdDto.from(sentimentAndChoiceInstance.getId()),
                UsageDto.from(sentimentAndChoiceInstance.getUsage()),
                sentimentAndChoiceInstance.getLabelSet(),
                sentimentAndChoiceInstance.getNonLabel());
    }
    
}
