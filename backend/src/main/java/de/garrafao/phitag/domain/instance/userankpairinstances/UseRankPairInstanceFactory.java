package de.garrafao.phitag.domain.instance.userankpairinstances;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class UseRankPairInstanceFactory {

    private String instanceId;

    private Phase phase;

    private Usage firstUsage;
    private Usage secondUsage;
    private Usage thirdUsage;
    private Usage fourthUsage;

    private Usage fifthUsage;
    private Usage sixthUsage;
    private Usage seventhUsage;
    private Usage eightUsage;
    private Usage ninthUsage;
    private Usage tenthUsage;


    private String labelSet;

    private String nonLabel;

    public UseRankPairInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public UseRankPairInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public UseRankPairInstanceFactory withFirstUsage(final Usage firstUsage) {
        this.firstUsage = firstUsage;
        return this;
    }

    public UseRankPairInstanceFactory withSecondUsage(final Usage secondUsage) {
        this.secondUsage = secondUsage;
        return this;
    }

    public UseRankPairInstanceFactory withThirdUsage(final Usage thirdUsage) {
        this.thirdUsage = thirdUsage;
        return this;
    }
    public UseRankPairInstanceFactory withFourthUsage(final Usage fourthUsage) {
        this.fourthUsage = fourthUsage;
        return this;
    }

    public UseRankPairInstanceFactory withFifthUsage(final Usage fifthUsage) {
        this.fifthUsage = fifthUsage;
        return this;
    }

    public UseRankPairInstanceFactory withSixthUsage(final Usage sixthUsage) {
        this.sixthUsage = sixthUsage;
        return this;
    }

    public UseRankPairInstanceFactory withSeventhUsage(final Usage seventhUsage) {
        this.seventhUsage = seventhUsage;
        return this;
    }
    public UseRankPairInstanceFactory withEightUsage(final Usage eightUsage) {
        this.eightUsage = eightUsage;
        return this;
    }
    public UseRankPairInstanceFactory withNinthUsage(final Usage ninthUsage) {
        this.ninthUsage = ninthUsage;
        return this;
    }
    public UseRankPairInstanceFactory withTenthUsage(final Usage tenthUsage) {
        this.tenthUsage = tenthUsage;
        return this;
    }


    public UseRankPairInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public UseRankPairInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public UseRankPairInstance build() {
        return new UseRankPairInstance(instanceId, phase, firstUsage, secondUsage, thirdUsage, fourthUsage,
                fifthUsage, sixthUsage, seventhUsage, eightUsage, ninthUsage, tenthUsage, labelSet, nonLabel);
    }

}
