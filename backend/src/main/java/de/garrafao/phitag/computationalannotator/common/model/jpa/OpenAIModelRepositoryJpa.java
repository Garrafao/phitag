package de.garrafao.phitag.computationalannotator.common.model.jpa;

import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenAIModelRepositoryJpa extends JpaRepository<OpenAIModel, Integer> {
    
    Optional<OpenAIModel> findByName(String name);

}
