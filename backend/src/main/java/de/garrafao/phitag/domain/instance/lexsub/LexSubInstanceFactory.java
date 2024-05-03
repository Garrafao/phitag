package de.garrafao.phitag.domain.instance.lexsub;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class LexSubInstanceFactory {

    private String instanceId;
    private Phase phase;

    private Usage usage;

    private String labelSet;
    private String nonLabel;

    public LexSubInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public LexSubInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public LexSubInstanceFactory withUsage(final Usage usage) {
        this.usage = usage;
        return this;
    }

    public LexSubInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public LexSubInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public LexSubInstance build() {
        return new LexSubInstance(instanceId, phase, usage, labelSet, nonLabel);
    }
    
}
