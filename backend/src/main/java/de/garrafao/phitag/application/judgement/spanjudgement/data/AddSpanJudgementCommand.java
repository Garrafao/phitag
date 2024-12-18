package de.garrafao.phitag.application.judgement.spanjudgement.data;

import de.garrafao.phitag.application.judgement.data.IAddJudgementCommand;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

@Getter
public class AddSpanJudgementCommand implements IAddJudgementCommand {

    private final String owner;
    private final String project;
    private final String phase;

    private final String instance;

    private final String label;
    private final String comment;

    public AddSpanJudgementCommand(String owner, String project, String phase, String instance, String label,
                                   String comment) {
        Validate.notNull(owner, "owner must not be null");
        Validate.notNull(project, "project must not be null");
        Validate.notNull(phase, "phase must not be null");
        Validate.notNull(instance, "instance must not be null");

        this.owner = owner;
        this.project = project;
        this.phase = phase;
        this.instance = instance;

        this.label = label;
        this.comment = comment;
    }
}
