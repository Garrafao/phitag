package de.garrafao.phitag.domain.instance.wssiminstance;

import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;

public class WSSIMInstanceFactory {

    private String instanceId;
    private Phase phase;

    private Usage usage;
    private WSSIMTag tag;

    private String labelSet;
    private String nonLabel;

    public WSSIMInstanceFactory withInstanceId(final String instanceId) {
        this.instanceId = instanceId;
        return this;
    }

    public WSSIMInstanceFactory withPhase(final Phase phase) {
        this.phase = phase;
        return this;
    }

    public WSSIMInstanceFactory withUsage(final Usage usage) {
        this.usage = usage;
        return this;
    }

    public WSSIMInstanceFactory withTag(final WSSIMTag tag) {
        this.tag = tag;
        return this;
    }

    public WSSIMInstanceFactory withLabelSet(final String labelSet) {
        this.labelSet = labelSet;
        return this;
    }

    public WSSIMInstanceFactory withNonLabel(final String nonLabel) {
        this.nonLabel = nonLabel;
        return this;
    }

    public WSSIMInstance build() {
        return new WSSIMInstance(instanceId, phase, usage, tag, labelSet, nonLabel);
    }
    
}
