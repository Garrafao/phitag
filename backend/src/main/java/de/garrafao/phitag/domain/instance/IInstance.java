package de.garrafao.phitag.domain.instance;

import de.garrafao.phitag.domain.phase.Phase;

public interface IInstance {

    IInstanceId getId();

    Phase getPhase();

}
