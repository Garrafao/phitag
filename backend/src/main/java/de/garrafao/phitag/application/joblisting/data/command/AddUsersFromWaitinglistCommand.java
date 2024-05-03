package de.garrafao.phitag.application.joblisting.data.command;

import java.util.List;

import lombok.Getter;

@Getter
public class AddUsersFromWaitinglistCommand {

    private final String name;
    private final String owner;
    private final String project;

    private final List<String> users;
    
    public AddUsersFromWaitinglistCommand(final String name, final String owner, final String project, final List<String> users) {
        this.name = name;
        this.owner = owner;
        this.project = project;
        this.users = users;
    }
}
