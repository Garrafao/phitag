package de.garrafao.phitag.application.judgement.data;

public interface IEditJudgementCommand {

    String getOwner();
    String getProject();
    String getPhase();

    String getInstance();
    String getAnnotator();

    String getUUID();

    String getLabel();
    String getComment();


    
}
