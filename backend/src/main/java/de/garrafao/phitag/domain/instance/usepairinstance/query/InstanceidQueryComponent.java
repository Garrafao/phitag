package de.garrafao.phitag.domain.instance.usepairinstance.query;

import de.garrafao.phitag.domain.core.QueryComponent;
import lombok.Getter;

@Getter
public class InstanceidQueryComponent implements QueryComponent {

    private final String instanceid;

    public InstanceidQueryComponent(final String instanceid) {
        this.instanceid = instanceid;
    }
    
}
