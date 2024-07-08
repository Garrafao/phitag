package de.garrafao.phitag.application.phitagdata.usage.data;

import lombok.Getter;

@Getter
public class DeleteUsageCommand {
    private final String dataid;
    private final String project;
    private final String owner;

    private DeleteUsageCommand(
            final String dataid,
            final String project,
            final String owner) {
        this.dataid = dataid;
        this.project = project;
        this.owner = owner;
    }

}