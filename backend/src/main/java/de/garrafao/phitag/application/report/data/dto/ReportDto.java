package de.garrafao.phitag.application.report.data.dto;

import de.garrafao.phitag.application.status.data.StatusDto;
import de.garrafao.phitag.domain.report.Report;
import lombok.Getter;

@Getter
public class ReportDto {

    private final Integer id;
    private final String user;
    private final StatusDto status;
    private final String description;

    public ReportDto(final Integer id, final String user, final StatusDto status, final String description) {
        this.id = id;
        this.user = user;
        this.status = status;
        this.description = description;
    }

    public static ReportDto from(final Report report) {
        return new ReportDto(
            report.getId(), 
            report.getUser().getUsername(), 
            StatusDto.from(report.getStatus()), 
            report.getReportDescription());
    }


    
}
