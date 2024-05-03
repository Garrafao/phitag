package de.garrafao.phitag.domain.instance.userankrelative;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class UseRankRelativeInstanceFactory {

    private String instanceId;

    private Phase phase;

    private Usage firstUsage;
    private Usage secondUsage;
    private Usage thirdUsage;
    private Usage fourthUsage;

    private String labelSet;

    private String nonLabel;

    public UseRankRelativeInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public UseRankRelativeInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public UseRankRelativeInstanceFactory withFirstUsage(final Usage firstUsage) {
        this.firstUsage = firstUsage;
        return this;
    }

    public UseRankRelativeInstanceFactory withSecondUsage(final Usage secondUsage) {
        this.secondUsage = secondUsage;
        return this;
    }

    public UseRankRelativeInstanceFactory withThirdUsage(final Usage thirdUsage) {
        this.thirdUsage = thirdUsage;
        return this;
    }
    public UseRankRelativeInstanceFactory withFourthUsage(final Usage fourthUsage) {
        this.fourthUsage = fourthUsage;
        return this;
    }


    public UseRankRelativeInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public UseRankRelativeInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public UseRankRelativeInstance build() {
        return new UseRankRelativeInstance(instanceId, phase, firstUsage, secondUsage, thirdUsage, fourthUsage, labelSet, nonLabel);
    }

}
