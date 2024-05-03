package de.garrafao.phitag.domain.phitagdata;

import de.garrafao.phitag.domain.project.Project;

public interface IPhitagDataType {
    
    IPhitagDataTypeId getId();
    Project getProject();

}
