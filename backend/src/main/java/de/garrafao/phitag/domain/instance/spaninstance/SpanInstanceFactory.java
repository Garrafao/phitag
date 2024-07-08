package de.garrafao.phitag.domain.instance.spaninstance;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class SpanInstanceFactory {

    private String instanceId;
    private Phase phase;

    private Usage usage;

    private String labelSet;
    private String nonLabel;

    public SpanInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public SpanInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public SpanInstanceFactory withUsage(final Usage usage) {
        this.usage = usage;
        return this;
    }

    public SpanInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public SpanInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public SpanInstance build() {
        return new SpanInstance(instanceId, phase, usage, labelSet, nonLabel);
    }
    
}
