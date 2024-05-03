package de.garrafao.phitag.application.annotator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.annotator.data.command.CreateAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.command.EditAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.command.RemoveAnnotatorCommand;
import de.garrafao.phitag.application.annotator.data.dto.AnnotatorDto;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.entitlement.data.EntitlementEnum;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.annotator.error.AnnotatorExistException;
import de.garrafao.phitag.domain.annotator.query.AnnotatorQueryBuilder;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.entitlement.error.InvalidEntitlementException;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Service
public class AnnotatorApplicationService {

    // Repository

    private final AnnotatorRepository annotatorRepository;

    // Services

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public AnnotatorApplicationService(final AnnotatorRepository annotatorRepository,

            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,

            final CommonService commonService,
            final ValidationService validationService) {
        this.annotatorRepository = annotatorRepository;

        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getter

    /**
     * Returns the Entitlement of the requesting user for the given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @return a list of all {@AnnotatorDto}
     */
    public List<AnnotatorDto> getAnnotatorsOfProject(final String authenticationToken, final String owner,
            final String project) {
        User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        final Query query = new AnnotatorQueryBuilder().withOwner(owner).withProject(project).build();
        return annotatorRepository.findByQuery(query).stream().map(AnnotatorDto::from).collect(Collectors.toList());
    }

    public List<AnnotatorDto> getComputationalAnnotatorsOfPhase(final String authenticationToken, final String owner,
            final String project, final String phase) {
        User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        final Query query = new AnnotatorQueryBuilder()
                .withOwner(owner)
                .withProject(project)
                .withAnnotationType(phaseEntity.getAnnotationType().getName())
                .withLanguage(phaseEntity.getProject().getLanguage().getName())
                .withIsBot(true)
                .build();

        return annotatorRepository.findByQuery(query).stream().map(AnnotatorDto::from).collect(Collectors.toList());
    }

    /**
     * Returns the Entitlement of the requesting user for the given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @return
     */
    public String getAnnotatorEntitlement(final String authenticationToken, final String owner, final String project) {
        User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        final Optional<Annotator> annotator = this.annotatorRepository
                .findByIdUsernameAndIdProjectidNameAndIdProjectidOwnername(requester.getUsername(),
                        projectEntity.getId().getName(), projectEntity.getId().getOwnername());

        if (!annotator.isPresent()) {
            return "NONE";
        }

        return annotator.get().getEntitlement().toString();
    }

    // Setter

    /**
     * Creates a new Annotator for the given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param command             the command containing the data of the new
     *                            annotator
     */
    @Transactional
    public void add(final String authenticationToken, final CreateAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(command.getOwner(),
                command.getProject());
        final User user = this.commonService.getUser(command.getUsername());
        final Entitlement entitlement;

        if (user.isIsbot()) {
            entitlement = this.commonService.getEntitlement(EntitlementEnum.ENTITLEMENT_ADMIN.name());
        } else {
            entitlement = this.commonService.getEntitlement(command.getEntitlement());
        }

        this.validationService.projectAdminAccess(requester, projectEntity);
        if (this.commonService.isUserAnnotator(command.getOwner(), command.getProject(), command.getUsername())) {
            throw new AnnotatorExistException();
        }

        final Annotator newannotator = new Annotator(user, projectEntity, entitlement);
        this.annotatorRepository.save(newannotator);

        // Send notification
        this.commonService.sendNotification(user, "You have been added as an annotator to project "
                + projectEntity.getId().getName() + " by " + requester.getUsername() + ".");

        // Add annotatorstatistics
        this.annotatorStatisticApplicationService.initializeAnnotatorStatistic(newannotator);

    }

    /**
     * Deletes the annotator with the given username from the given project.
     * 
     * NOTE: This method is a future feature and is not implemented yet.
     * 
     * @param authenticationToken
     * @param command
     */
    @Transactional
    public void delete(final String authenticationToken, final RemoveAnnotatorCommand command) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Updates, currently only the entitlement, of the annotator with the given
     * username in the given project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param command             the command containing the data of the annotator
     */
    @Transactional
    public void update(final String authenticationToken, final EditAnnotatorCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(),
                command.getAnnotator());
        final Entitlement entitlement = this.commonService.getEntitlement(command.getEntitlement());

        this.validationService.projectAdminAccess(requester, annotator.getProject());

        // Do not allow owner and bots to have different entitlement than ADMIN
        if (annotator.getProject().getOwner().equals(annotator.getUser())
                && !entitlement.getName().equals(EntitlementEnum.ENTITLEMENT_ADMIN.name())) {
            throw new InvalidEntitlementException("Owner must have entitlement ADMIN");
        }

        if (annotator.getUser().isIsbot() && !entitlement.getName().equals(EntitlementEnum.ENTITLEMENT_ADMIN.name())) {
            throw new InvalidEntitlementException("Bots must have entitlement ADMIN");
        }

        annotator.setEntitlement(entitlement);
        this.annotatorRepository.save(annotator);

        this.commonService.sendNotification(annotator.getUser(),
                "Your entitlement in project " + annotator.getProject().getId().getName() + " has been changed to "
                        + entitlement.getVisiblename() + " by " + requester.getUsername() + ".");
    }
}
