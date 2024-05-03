package de.garrafao.phitag.application.phitagdata;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.statistics.projectstatic.ProjectStatisticApplicationService;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.phitagdata.usage.UsageApplicationService;
import de.garrafao.phitag.application.phitagdata.usage.data.EditUsageCommand;
import de.garrafao.phitag.application.phitagdata.usage.data.PagedUsageDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Service
public class PhitagDataApplicationService {

    // Repository

    // Services

    private final UsageApplicationService usageApplicationService;

    private final ProjectStatisticApplicationService projectStatisticApplicationService;

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public PhitagDataApplicationService(
            final UsageApplicationService usageApplicationService,
            final ProjectStatisticApplicationService projectStatisticApplicationService,
            final CommonService commonService,
            final ValidationService validationService) {
        this.usageApplicationService = usageApplicationService;
        this.projectStatisticApplicationService = projectStatisticApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API for Usage

    // Getters

    /**
     * Get all usages for a given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @return a list of all {@Usage} for the given project
     */
    public List<UsageDto> getUsageDto(final String authenticationToken, final String owner, final String project) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        return this.usageApplicationService.findByProject(projectEntity).stream().map(UsageDto::from).toList();
    }

    /**
     * Get all usages for a given project in a paged way.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param pagesize            the size of the page
     * @param pagenumber          the number of the page
     * @param orderBy             the field to order by
     * @return a paged list of all {@Usage} for the given project
     */
    public PagedUsageDto getPagedUsageDto(final String authenticationToken, final String owner, final String project,
            final int pagesize, final int pagenumber, final String orderBy) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        return PagedUsageDto
                .from(this.usageApplicationService.findByProject(projectEntity, pagesize, pagenumber, orderBy));
    }

    /**
     * Export all usages for a given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @return a InputStreamResource of the exported usages
     */
    public InputStreamResource exportUsages(final String authenticationToken, final String owner,
            final String project) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAdminAccess(requester, projectEntity);

        return this.usageApplicationService.exportUsage(projectEntity);
    }

    // Setters

    /**
     * Import usages for a given project from a file.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param file                the file to import
     */
    @Transactional
    public void addUsages(final String authenticationToken, final String owner, final String project,
            final MultipartFile file) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAdminAccess(requester, projectEntity);

        this.usageApplicationService.save(projectEntity, file);

        // Update project statistics
        this.projectStatisticApplicationService.updateProjectStatistic(projectEntity);
    }

    /**
     * Edit a usage.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param command             the command to edit the usage
     */
    @Transactional
    public void editUsage(final String authenticationToken, final EditUsageCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(command.getOwner(), command.getProject());

        this.validationService.projectAdminAccess(requester, projectEntity);

        this.usageApplicationService.edit(projectEntity, command);
    }

}
