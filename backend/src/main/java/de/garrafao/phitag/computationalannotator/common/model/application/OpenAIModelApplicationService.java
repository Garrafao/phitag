package de.garrafao.phitag.computationalannotator.common.model.application;

import de.garrafao.phitag.computationalannotator.common.model.application.data.OpenAIModelDto;
import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModel;
import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OpenAIModelApplicationService {

    private final OpenAIModelRepository openAIModelRepository;

    @Autowired
    public OpenAIModelApplicationService(OpenAIModelRepository openAIModelRepository) {
        this.openAIModelRepository = openAIModelRepository;
    }



    public Set<OpenAIModelDto> getOpenAIModelDtos() {
        return this.openAIModelRepository.findAll().stream().filter(OpenAIModel::getActive).map(OpenAIModelDto::from).collect(Collectors.toSet());
    }

}
