package de.garrafao.phitag.application.judgement.userankjudgement.data;

import de.garrafao.phitag.application.judgement.data.IDeleteJudgementCommand;
import lombok.Getter;

@Getter
public class DeleteUseRankJudgementCommand implements IDeleteJudgementCommand {

    private String owner;
    private String project;
    private String phase;

    private String instance;
    private String annotator;
    
    private String UUID;

    public DeleteUseRankJudgementCommand(String owner, String project, String phase, String instance, String annotator, String UUID) {
        this.owner = owner;
        this.project = project;
        this.phase = phase;

        this.instance = instance;
        this.annotator = annotator;
        
        this.UUID = UUID;
    }
    
}
