package de.garrafao.phitag.application.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.report.data.dto.PagedReportDto;
import de.garrafao.phitag.application.report.data.dto.ReportDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.report.Report;
import de.garrafao.phitag.domain.report.ReportRepository;
import de.garrafao.phitag.domain.report.error.ReportNotFoundException;
import de.garrafao.phitag.domain.report.query.ReportQueryBuilder;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.user.User;

@Service
public class ReportApplicationService {

    // Repository

    private final ReportRepository reportRepository;

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public ReportApplicationService(
            final ReportRepository reportRepository,
            final CommonService commonService, final ValidationService validationService) {
        this.reportRepository = reportRepository;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Find all Reports given a query
     * 
     * @param authenticationToken The authentication token
     * @param user                The user
     * @param status              The status
     * @param page                The page
     */
    public PagedReportDto findReports(final String authenticationToken, final String user, final String status,
            final Integer page) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        this.validationService.validateUserIsAdmin(requester);

        final Query query = new ReportQueryBuilder().withUser(user).withStatus(status).build();
        final PageRequestWraper pageRequest = new PageRequestWraper(50, page, "id");

        final Page<Report> reports = this.reportRepository.findByQueryPaged(query, pageRequest);

        return PagedReportDto.from(reports);
    }

    /**
     * Find a Report for requesting user
     * 
     * @param authenticationToken
     * @param status
     * @param page
     */
    public PagedReportDto findReportsByUser(final String authenticationToken, final String status, final Integer page) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Query query = new ReportQueryBuilder().withUser(requester.getUsername()).withStatus(status).build();
        final PageRequestWraper pageRequest = new PageRequestWraper(50, page, "id");

        final Page<Report> reports = this.reportRepository.findByQueryPaged(query, pageRequest);

        return PagedReportDto.from(reports);
    }


    /**
     * Add a new Report
     * 
     * @param authenticationToken The authentication token
     * @param description         The description
     */
    public void addReport(final String authenticationToken, final String description) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Report report = new Report(requester, description,
                this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name()));
        this.reportRepository.save(report);
    }

    /**
     * Update a Report
     * 
     * @param authenticationToken The authentication token
     * @param id                  The id
     * @param description         The description
     * @param status              The status
     */
    public void updateReport(final String authenticationToken, final Integer id, final String description,
            final String status) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        this.validationService.validateUserIsAdmin(requester);

        final Report report = this.reportRepository.findById(id).orElseThrow(ReportNotFoundException::new);

        report.setStatus(this.commonService.getStatus(status));
        report.setReportDescription(description);

        this.reportRepository.save(report);
    }

}
