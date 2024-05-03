package de.garrafao.phitag.application.judgement.userankrelativejudgement.data;

import de.garrafao.phitag.application.judgement.data.IEditJudgementCommand;
import lombok.Getter;

@Getter
public class EditUseRankRelativeJudgementCommand implements IEditJudgementCommand {

    private String owner;
    private String project;
    private String phase;

    private String instance;
    private String annotator;
    
    private String UUID;

    private String label;
    private String comment;

    public EditUseRankRelativeJudgementCommand(String owner, String project, String phase, String instance, String annotator, String UUID, String label, String comment) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.instance = instance;
        this.annotator = annotator;
        
        this.UUID = UUID;
        
        this.label = label;
        this.comment = comment;
    }
    
}
