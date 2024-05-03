package de.garrafao.phitag.application.judgement.data;

public interface IAddJudgementCommand {    
    String getOwner();
    String getProject();
    String getPhase();
    String getInstance();
}
