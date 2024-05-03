package de.garrafao.phitag.computationalannotator.common.command;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class ComputationalAnnotatorCommand {
    private final String owner;

    private final String project;

    private final String phase;

    private final String apiKey;

    private final String model;

    private final String prompt;

    public ComputationalAnnotatorCommand(String owner, String project, String phase, String apiKey, String model, String prompt) {
        Validate.notBlank(owner, "Owner must not be blank");
        Validate.notBlank(project, "Project must not be blank");
        Validate.notBlank(phase, "Phase must not be blank");
        Validate.notBlank(apiKey, "APi key must not be blank");
        Validate.notBlank(model, "model must not be blank");
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.apiKey = apiKey;
        this.model = model;
        this.prompt = prompt;

    }

    @Override
    public String toString() {
        return "ComputationalAnnotatorCommand{" +
                "owner='" + owner + '\'' +
                ", project='" + project + '\'' +
                ", phase='" + phase + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}
