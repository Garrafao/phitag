package de.garrafao.phitag.computationalannotator.rest;

import de.garrafao.phitag.computationalannotator.common.model.application.OpenAIModelApplicationService;
import de.garrafao.phitag.computationalannotator.common.model.application.data.OpenAIModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/computationalannotator")
public class OpenAIModelResources {

    private final OpenAIModelApplicationService openAIModelApplicationService;

    @Autowired
    public OpenAIModelResources(OpenAIModelApplicationService openAIModelApplicationService) {
        this.openAIModelApplicationService = openAIModelApplicationService;
    }


    @GetMapping("/model")
    public Set<OpenAIModelDto> all() {
        return this.openAIModelApplicationService.getOpenAIModelDtos();
    }

}
