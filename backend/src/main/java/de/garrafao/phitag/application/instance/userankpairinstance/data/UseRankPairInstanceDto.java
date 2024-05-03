package de.garrafao.phitag.application.instance.userankpairinstance.data;

import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import lombok.Getter;

import java.util.List;

@Getter
public class UseRankPairInstanceDto implements IInstanceDto {

    private final UseRankPairInstanceIdDto id;

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

    private UseRankPairInstanceDto(
            final UseRankPairInstanceIdDto id,
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

    public static UseRankPairInstanceDto from(final UseRankPairInstance useRankPairInstance) {
        if (useRankPairInstance == null) {
            return null;
        }

        return new UseRankPairInstanceDto(
                UseRankPairInstanceIdDto.from(useRankPairInstance.getId()),
                useRankPairInstance.getFirstusage() != null ? UsageDto.from(useRankPairInstance.getFirstusage()) : null,
                useRankPairInstance.getSecondusage() != null ? UsageDto.from(useRankPairInstance.getSecondusage()) : null,
                useRankPairInstance.getThirdusage() != null ? UsageDto.from(useRankPairInstance.getThirdusage()) : null,
                useRankPairInstance.getFourthusage() != null ? UsageDto.from(useRankPairInstance.getFourthusage()) : null,
                useRankPairInstance.getFifthusage() != null ? UsageDto.from(useRankPairInstance.getFifthusage()) : null,
                useRankPairInstance.getSixthusage() != null ? UsageDto.from(useRankPairInstance.getSixthusage()) : null,
                useRankPairInstance.getSeventhusage() != null ? UsageDto.from(useRankPairInstance.getSeventhusage()) : null,
                useRankPairInstance.getEightusage()!= null ? UsageDto.from(useRankPairInstance.getEightusage()) : null,
                useRankPairInstance.getNinthusage()!= null ? UsageDto.from(useRankPairInstance.getNinthusage()) : null,
                useRankPairInstance.getTenthusage() != null ? UsageDto.from(useRankPairInstance.getTenthusage()) : null,
                useRankPairInstance.getLabelSet(),
                useRankPairInstance.getNonLabel()
        );
    }
}
