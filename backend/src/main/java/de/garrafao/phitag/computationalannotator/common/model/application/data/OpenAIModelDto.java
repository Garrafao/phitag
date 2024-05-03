package de.garrafao.phitag.computationalannotator.common.model.application.data;

import de.garrafao.phitag.computationalannotator.common.model.domain.OpenAIModel;
import lombok.Getter;

@Getter
public class OpenAIModelDto {

    private final String name;
    private final String visiblename;

    private OpenAIModelDto(final String name, final String visiblename) {
        this.name = name;
        this.visiblename = visiblename;
    }

    public static OpenAIModelDto from(OpenAIModel openAIModel) {
        return new OpenAIModelDto(openAIModel.getName(), openAIModel.getVisiblename());
    }
    
}
