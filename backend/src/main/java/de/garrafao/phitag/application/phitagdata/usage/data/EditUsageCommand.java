package de.garrafao.phitag.application.phitagdata.usage.data;

import lombok.Getter;

@Getter
public class EditUsageCommand {

    private final String dataid;
    private final String project;
    private final String owner;

    private final String context;

    private final String indexTargetToken;
    private final String indexTargetSentence;

    private final String lemma;
    private final String group;

    private EditUsageCommand(
            final String dataid,
            final String project,
            final String owner,

            final String context,
            
            final String indexTargetToken,
            final String indexTargetSentence,
            
            final String lemma,
            final String group) {
        this.dataid = dataid;
        this.project = project;
        this.owner = owner;

        this.context = context;
        
        this.indexTargetToken = indexTargetToken;
        this.indexTargetSentence = indexTargetSentence;
        
        this.lemma = lemma;
        this.group = group;
    }
    
}
