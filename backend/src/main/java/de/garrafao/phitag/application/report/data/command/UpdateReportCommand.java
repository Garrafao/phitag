package de.garrafao.phitag.application.report.data.command;

import lombok.Getter;

@Getter
public class UpdateReportCommand {

    private final Integer id;
    private final String status;
    private final String description;

    public UpdateReportCommand(final Integer id, final String status, final String description) {
        this.id = id;
        this.status = status;
        this.description = description;
    }
    
}
