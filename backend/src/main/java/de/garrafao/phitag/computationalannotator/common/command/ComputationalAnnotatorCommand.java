package de.garrafao.phitag.computationalannotator.common.command;

import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class ComputationalAnnotatorCommand {
    private final String owner;

    private final String project;

    private final String phase;

    private final String apiKey;

    private final double temperature;

    private final double topP;

    private final String model;

    private final String prompt;

    private final String system;

    private final String finalMessage;
    public ComputationalAnnotatorCommand(String owner, String project, String phase, String apiKey, String model, double temperature,
                                          double topP, String prompt, String system, String finalMessage) {
        Validate.notBlank(owner, "Owner must not be blank");
        Validate.notBlank(project, "Project must not be blank");
        Validate.notBlank(phase, "Phase must not be blank");
        Validate.notBlank(apiKey, "API key must not be blank");
        Validate.notBlank(model, "model must not be blank");
        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.apiKey = apiKey;
        this.model = model;
        this.temperature = temperature;
        this.topP = topP;
        this.prompt = prompt;
        this.system = system;
        this.finalMessage = finalMessage;

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
