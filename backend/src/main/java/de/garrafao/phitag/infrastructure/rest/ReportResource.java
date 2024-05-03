package de.garrafao.phitag.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.report.ReportApplicationService;
import de.garrafao.phitag.application.report.data.command.UpdateReportCommand;
import de.garrafao.phitag.application.report.data.dto.PagedReportDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/api/v1/report")
public class ReportResource {

    private final ReportApplicationService reportApplicationService;

    @Autowired
    public ReportResource(final ReportApplicationService reportApplicationService) {
        this.reportApplicationService = reportApplicationService;
    }

    /**
     * Find all Reports given a query
     * 
     * @param authenticationToken The authentication token
     * @param user                The user
     * @param status              The status
     * @param page                The page
     */
    @GetMapping(value = "/find")
    public PagedReportDto findReports(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "user", required = false) final String user,
            @RequestParam(value = "status", required = false) final String status,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {
        return this.reportApplicationService.findReports(authenticationToken, user, status, page);
    }

    /**
     * Find all Reports for the current user
     * 
     * @param authenticationToken The authentication token
     * @param status              The status
     * @param page                The page
     */
    @GetMapping(value = "/find/user")
    public PagedReportDto findMyReports(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "status", required = false) final String status,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {
        return this.reportApplicationService.findReportsByUser(authenticationToken, status, page);
    }

    /**
     * Add a new Report
     * 
     * @param authenticationToken The authentication token
     * @param description         The description
     */
    @PostMapping(value="/add")
    public void addReport(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "description") final String description) {
        this.reportApplicationService.addReport(authenticationToken, description);
    }

    /**
     * Update a Report
     * 
     * @param authenticationToken The authentication token
     * @param id                  The id
     * @param description         The description
     * @param status              The status
     */
    @PostMapping(value="/update")
    public void updateReport(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestBody final UpdateReportCommand updateReportCommand) {
        this.reportApplicationService.updateReport(authenticationToken, updateReportCommand.getId(), updateReportCommand.getDescription(), updateReportCommand.getStatus());
    }
    
}
