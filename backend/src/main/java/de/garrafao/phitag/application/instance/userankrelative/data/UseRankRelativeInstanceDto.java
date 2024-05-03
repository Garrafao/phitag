package de.garrafao.phitag.application.instance.userankrelative.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class UseRankRelativeInstanceDto implements IInstanceDto {

    private final UseRankRelativeInstanceIdDto id;

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

    private UseRankRelativeInstanceDto(
            final UseRankRelativeInstanceIdDto id,
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

    public static UseRankRelativeInstanceDto from(final UseRankRelativeInstance useRankRelativeInstance) {
        if (useRankRelativeInstance == null) {
            return null;
        }

        return new UseRankRelativeInstanceDto(
                UseRankRelativeInstanceIdDto.from(useRankRelativeInstance.getId()),
                useRankRelativeInstance.getFirstusage() != null ? UsageDto.from(useRankRelativeInstance.getFirstusage()) : null,
                useRankRelativeInstance.getSecondusage() != null ? UsageDto.from(useRankRelativeInstance.getSecondusage()) : null,
                useRankRelativeInstance.getThirdusage() != null ? UsageDto.from(useRankRelativeInstance.getThirdusage()) : null,
                useRankRelativeInstance.getFourthusage() != null ? UsageDto.from(useRankRelativeInstance.getFourthusage()) : null,
                useRankRelativeInstance.getFifthusage() != null ? UsageDto.from(useRankRelativeInstance.getFifthusage()) : null,
                useRankRelativeInstance.getSixthusage() != null ? UsageDto.from(useRankRelativeInstance.getSixthusage()) : null,
                useRankRelativeInstance.getSeventhusage() != null ? UsageDto.from(useRankRelativeInstance.getSeventhusage()) : null,
                useRankRelativeInstance.getEightusage()!= null ? UsageDto.from(useRankRelativeInstance.getEightusage()) : null,
                useRankRelativeInstance.getNinthusage()!= null ? UsageDto.from(useRankRelativeInstance.getNinthusage()) : null,
                useRankRelativeInstance.getTenthusage() != null ? UsageDto.from(useRankRelativeInstance.getTenthusage()) : null,
                useRankRelativeInstance.getLabelSet(),
                useRankRelativeInstance.getNonLabel()
        );
    }
}
