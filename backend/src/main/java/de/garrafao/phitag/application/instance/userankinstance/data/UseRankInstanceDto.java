package de.garrafao.phitag.application.instance.userankinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class UseRankInstanceDto implements IInstanceDto {

    private final UseRankInstanceIdDto id;

    private final UsageDto firstusage;
    private final UsageDto secondusage;
    private final UsageDto thirdusage;
    private final UsageDto fourthusage;
    private final UsageDto fifthusage;
    private final UsageDto sixthusage;
    private final UsageDto seventhusage;
    private final UsageDto eightusage;
    private final UsageDto ninthusage;
    private final UsageDto tenthusage;

    private final List<String> labelSet;
    private final String nonLabel;

    private UseRankInstanceDto(
            final UseRankInstanceIdDto id,
            final UsageDto firstusage,
            final UsageDto secondusage,
            final UsageDto thirdusage,
            final UsageDto fourthusage,
            final UsageDto fifthusage,
            final UsageDto sixthusage,
            final UsageDto seventhusage,
            final UsageDto eightusage,
            final UsageDto ninthusage,
            final UsageDto tenthusage,
            final List<String> labelSet,
            final String nonLabel) {
        this.id = id;
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage = fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.eightusage = eightusage;
        this.ninthusage = ninthusage;
        this.tenthusage = tenthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    public static UseRankInstanceDto from(final UseRankInstance useRankInstance) {
        if (useRankInstance == null) {
            return null;
        }

        return new UseRankInstanceDto(
                UseRankInstanceIdDto.from(useRankInstance.getId()),
                useRankInstance.getFirstusage() != null ? UsageDto.from(useRankInstance.getFirstusage()) : null,
                useRankInstance.getSecondusage() != null ? UsageDto.from(useRankInstance.getSecondusage()) : null,
                useRankInstance.getThirdusage() != null ? UsageDto.from(useRankInstance.getThirdusage()) : null,
                useRankInstance.getFourthusage() != null ? UsageDto.from(useRankInstance.getFourthusage()) : null,
                useRankInstance.getFifthusage() != null ? UsageDto.from(useRankInstance.getFifthusage()) : null,
                useRankInstance.getSixthusage() != null ? UsageDto.from(useRankInstance.getSixthusage()) : null,
                useRankInstance.getSeventhusage() != null ? UsageDto.from(useRankInstance.getSeventhusage()) : null,
                useRankInstance.getEightusage()!= null ? UsageDto.from(useRankInstance.getEightusage()) : null,
                useRankInstance.getNinthusage()!= null ? UsageDto.from(useRankInstance.getNinthusage()) : null,
                useRankInstance.getTenthusage() != null ? UsageDto.from(useRankInstance.getTenthusage()) : null,
                useRankInstance.getLabelSet(),
                useRankInstance.getNonLabel()
        );
    }
}
