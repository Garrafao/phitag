package de.garrafao.phitag.domain.instance.usepairinstance;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Data;

public class UsePairInstanceFactory {

    private String instanceId;
    private Phase phase;

    private Usage firstUsage;
    private Usage secondUsage;

    private String labelSet;
    private String nonLabel;

    public UsePairInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public UsePairInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public UsePairInstanceFactory withFirstUsage(final Usage firstUsage) {
        this.firstUsage = firstUsage;
        return this;
    }

    public UsePairInstanceFactory withSecondUsage(final Usage secondUsage) {
        this.secondUsage = secondUsage;
        return this;
    }

    public UsePairInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public UsePairInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public UsePairInstance build() {
        return new UsePairInstance(instanceId, phase, firstUsage, secondUsage, labelSet, nonLabel);
    }

}
