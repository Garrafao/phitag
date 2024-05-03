package de.garrafao.phitag.application.instance.data;

import de.garrafao.phitag.domain.instance.IInstance;

public interface IInstanceDto {
    
    public IInstanceIdDto getId();

    public static IInstanceDto from(final IInstance instance) {
        return null;
    }

}
