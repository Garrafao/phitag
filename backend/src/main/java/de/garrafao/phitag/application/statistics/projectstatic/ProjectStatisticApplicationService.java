package de.garrafao.phitag.application.statistics.projectstatic;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.statistics.projectstatic.data.ProjectStatisticDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatistic;
import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatisticRepository;
import de.garrafao.phitag.domain.user.User;

@Service
public class ProjectStatisticApplicationService {

    // Repository

    private final ProjectStatisticRepository projectStatisticRepository;

    private final UsageRepository usageRepository;

    // Lazy loading

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public ProjectStatisticApplicationService(
            final ProjectStatisticRepository projectStatisticRepository,
            final UsageRepository usageRepository,

            @Lazy final CommonService commonService,
            @Lazy final ValidationService validationService) {
        this.projectStatisticRepository = projectStatisticRepository;
        this.usageRepository = usageRepository;

        // lazy loading
        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getter

    /**
     * Returns the project statistic for the given project.
     * 
     * @param authenticationToken the authentication token
     * @param username            the username of the user
     * @param projectname         the projectname of the project
     * @return the project statistic as a {@link ProjectStatisticDto}
     */
    public ProjectStatisticDto getProjectStatistic(final String authenticationToken, final String username,
            final String projectname) {
        final Project project = this.commonService.getProject(username, projectname);
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService.projectAccess(requester, project);

        // validate project statistic exists
        final ProjectStatistic projectStatistic = this.projectStatisticRepository
                .findByUsernameAndProjectname(username, projectname)
                .orElseThrow(() -> new StatisticException("Project statistic does not exist"));

        // return project statistic
        return ProjectStatisticDto.from(projectStatistic);
    }

    // Setter
    /**
     * Initializes the project statistic for the given project.
     * 
     * @param project the project
     */
    @Transactional
    public void initializeProjectStatistic(final Project project) {
        projectStatisticRepository
                .save(new ProjectStatistic(project.getId().getOwnername(), project.getId().getName()));
    }

    // Helper methods

    /**
     * Updates the project statistic for the given project.
     * 
     * @param project the project
     */
    @Transactional
    public void updateProjectStatistic(final Project project) {
        final ProjectStatistic projectStatistic = this.projectStatisticRepository
                .findByUsernameAndProjectname(project.getId().getOwnername(), project.getId().getName())
                .orElseThrow(() -> new StatisticException("Project statistic does not exist"));

        // update project statistic
        projectStatistic.setUsagecount(this.usageRepository.countByIdProjectidNameAndIdProjectidOwnername(
                project.getId().getName(), project.getId().getOwnername()));
        projectStatistic.setLemmacount(this.usageRepository.countDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(
                project.getId().getName(), project.getId().getOwnername()));

        Map<String, Integer> lemmaCountMap = new HashMap<>();
        this.usageRepository.findDistinctLemmaByIdProjectidNameAndIdProjectidOwnername(project.getId().getName(),
                project.getId().getOwnername()).forEach(
                        lemma -> lemmaCountMap.put(lemma,
                                this.usageRepository.countDistinctLemmaByLemmaAndIdProjectidNameAndIdProjectidOwnername(
                                        lemma, project.getId().getName(), project.getId().getOwnername())));

        projectStatistic.setUsagesPerLemmaCountMap(lemmaCountMap);

        // save project statistic
        this.projectStatisticRepository.save(projectStatistic);

    }

}
