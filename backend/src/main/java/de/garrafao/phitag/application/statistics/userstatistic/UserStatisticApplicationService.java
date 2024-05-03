package de.garrafao.phitag.application.statistics.userstatistic;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.statistics.userstatistic.data.UserStatisticDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.statistic.error.StatisticException;
import de.garrafao.phitag.domain.statistic.userstatistic.UserStatistic;
import de.garrafao.phitag.domain.statistic.userstatistic.UserStatisticRepository;
import de.garrafao.phitag.domain.user.User;

@Service
public class UserStatisticApplicationService {

    // Repository

    private final UserStatisticRepository userStatisticRepository;

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public UserStatisticApplicationService(
            final UserStatisticRepository userStatisticRepository,
            @Lazy final CommonService commonService,
            @Lazy final ValidationService validationService) {
        this.userStatisticRepository = userStatisticRepository;

        // lazy loading
        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getter

    /**
     * Returns the user statistic for the given user.
     * 
     * @param username
     *                 the username of the user
     * @return the user statistic as a {@link UserStatisticDto}
     */
    public UserStatisticDto getUserStatistic(final String authenticationToken, final String username) {
        // validate user exists, active and visible
        final User user = this.commonService.getUser(username);
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        if (!user.equals(requester) && (!user.isEnabled() || user.getVisibility().getName().equals(VisibilityEnum.VISIBILITY_PRIVATE.name()))) {
            throw new StatisticException("User is not active or visible");
        }

        UserStatistic statistic = userStatisticRepository.findByUsername(username)
                .orElseThrow(() -> new StatisticException("No user statistic found for user"));
        UserStatisticDto statisticDto = UserStatisticDto.from(statistic);

        Map<String, Integer> annotationTypeCountMap = new HashMap<String, Integer>();
        statistic.getAnnotationTypeCountMap().forEach((k, v) -> {
            if (v > 0) {
                annotationTypeCountMap.put(this.commonService.getAnnotationType(k).getVisiblename(), v);
            }
        });
        statisticDto.setAnnotationTypeCountMap(annotationTypeCountMap);

        return statisticDto;

    }

    // Setter

    // Helper methods

    /**
     * Initializes the user statistic for the given user.
     * 
     * @param user the user for which the user statistic should be initialized
     */
    @Transactional
    public void initializeUserStatistic(final User user) {
        userStatisticRepository.save(new UserStatistic(user.getUsername()));
    }

    /**
     * Update project related statistics, for a newly created project, for the
     * owner.
     * This statisic updates the following values:
     * - projectcount
     * - languageCountMap
     * 
     * E.g. the number of projects the user has created.
     * 
     * @param project the project for which the user statistic should be updated
     */
    @Transactional
    public void updateProjectRelatedStatistics(final Project project) {
        // fetch user statistic
        final UserStatistic userStatistic = userStatisticRepository.findByUsername(project.getId().getOwnername())
                .orElseThrow(() -> new StatisticException("No user statistic found for user"));

        // update user statistic
        userStatistic.setProjectcount(userStatistic.getProjectcount() + 1);

        Map<String, Integer> languageCountMap = userStatistic.getLanguageCountMap();
        languageCountMap.put(project.getLanguage().getName(),
                languageCountMap.getOrDefault(project.getLanguage().getName(), 0) + 1);

        // save user statistic
        userStatisticRepository.save(userStatistic);
    }

    /**
     * Update phase related statistics, for a newly created phase, for the given
     * user.
     * This statisic updates the following values:
     * - annotationTypeCountMap
     * 
     * @param phase the phase for which the user statistic should be updated
     */
    @Transactional
    public void updatePhaseRelatedStatistics(final Phase phase) {
        // fetch user statistic
        final UserStatistic userStatistic = userStatisticRepository
                .findByUsername(phase.getId().getProjectid().getOwnername())
                .orElseThrow(() -> new StatisticException("No user statistic found for user"));

        // update user statistic
        Map<String, Integer> annotationTypeCountMap = userStatistic.getAnnotationTypeCountMap();
        annotationTypeCountMap.put(phase.getAnnotationType().getName(),
                annotationTypeCountMap.getOrDefault(phase.getAnnotationType().getName(), 0) + 1);

        // save user statistic
        userStatisticRepository.save(userStatistic);
    }

    /**
     * Update/increments annotation count for a given project of the user.
     * 
     * @param project the project for which the annotation count should be
     *                incremented
     */
    @Transactional
    public void incrementAnnotationCountProject(final Project project) {
        // fetch user statistic
        final UserStatistic userStatistic = userStatisticRepository.findByUsername(project.getOwner().getUsername())
                .orElseThrow(() -> new StatisticException("No user statistic found for user"));

        // update user statistic
        Map<String, Integer> pojectAnnotationCountMap = userStatistic.getPojectAnnotationCountMap();
        pojectAnnotationCountMap.put(project.getId().getName(),
                pojectAnnotationCountMap.getOrDefault(project.getId().getName(), 0) + 1);

        // save user statistic
        userStatisticRepository.save(userStatistic);
    }

}
