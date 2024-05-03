package de.garrafao.phitag.domain.core;

import java.util.List;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class Query {
    
    private final List<QueryComponent> components;

    public Query(final List<QueryComponent> components) {
        Validate.noNullElements(components);
        this.components = components;
    }

}
