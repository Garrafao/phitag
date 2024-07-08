package de.garrafao.phitag.application.instance.data;

import lombok.Getter;

@Getter
public class DeleteInstanceCommand {
    private String instanceID;
    private String owner;
    private String project;
    private String phase;


    public DeleteInstanceCommand(String instanceID, String owner, String project, String phase) {
        this.instanceID = instanceID;
        this.owner = owner;
        this.project = project;
        this.phase = phase;
    }


}
