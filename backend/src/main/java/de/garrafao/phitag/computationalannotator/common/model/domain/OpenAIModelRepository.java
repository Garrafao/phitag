package de.garrafao.phitag.computationalannotator.common.model.domain;

import java.util.Optional;
import java.util.Set;

public interface OpenAIModelRepository {

    public Set<OpenAIModel> findAll();
        
    public Optional<OpenAIModel> findByName(String name);
        
}
