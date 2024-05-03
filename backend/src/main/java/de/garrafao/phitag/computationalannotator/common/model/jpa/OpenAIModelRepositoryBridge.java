package de.garrafao.phitag.computationalannotator.common.model.jpa;

import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModel;
import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class OpenAIModelRepositoryBridge implements OpenAIModelRepository {

    private final OpenAIModelRepositoryJpa openAIModelRepositoryJpa;

    @Autowired
    public OpenAIModelRepositoryBridge(OpenAIModelRepositoryJpa openAIModelRepositoryJpa) {
        this.openAIModelRepositoryJpa = openAIModelRepositoryJpa;
    }

    @Override
    public Optional<OpenAIModel> findByName(String name) {
        return this.openAIModelRepositoryJpa.findByName(name);
    }

    @Override
    public Set<OpenAIModel> findAll() {
        return new HashSet<>(this.openAIModelRepositoryJpa.findAll());
    }
    
}
