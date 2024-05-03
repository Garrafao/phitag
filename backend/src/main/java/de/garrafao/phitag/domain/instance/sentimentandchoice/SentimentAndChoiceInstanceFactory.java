package de.garrafao.phitag.domain.instance.sentimentandchoice;

import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class SentimentAndChoiceInstanceFactory {

    private String instanceId;
    private Phase phase;

    private Usage usage;

    private String labelSet;
    private String nonLabel;

    public SentimentAndChoiceInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public SentimentAndChoiceInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public SentimentAndChoiceInstanceFactory withUsage(final Usage usage) {
        this.usage = usage;
        return this;
    }

    public SentimentAndChoiceInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public SentimentAndChoiceInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public SentimentAndChoiceInstance build() {
        return new SentimentAndChoiceInstance(instanceId, phase, usage, labelSet, nonLabel);
    }
    
}
