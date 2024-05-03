package de.garrafao.phitag.application.judgement.data;

public interface IDeleteJudgementCommand {
    String getOwner();
    String getProject();
    String getPhase();

    String getInstance();
    String getAnnotator();

    String getUUID();
}
