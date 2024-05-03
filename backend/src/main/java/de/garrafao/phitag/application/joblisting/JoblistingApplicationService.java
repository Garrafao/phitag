package de.garrafao.phitag.application.joblisting;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.entitlement.data.EntitlementEnum;
import de.garrafao.phitag.application.joblisting.data.command.AddUsersFromWaitinglistCommand;
import de.garrafao.phitag.application.joblisting.data.command.CreateJoblistingCommand;
import de.garrafao.phitag.application.joblisting.data.command.JoinJoblistingCommand;
import de.garrafao.phitag.application.joblisting.data.dto.JoblistingDto;
import de.garrafao.phitag.application.joblisting.data.dto.PersonalJoblistingDto;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.joblisting.Joblisting;
import de.garrafao.phitag.domain.joblisting.JoblistingRepository;
import de.garrafao.phitag.domain.joblisting.error.JoblistingAlreadyExistsException;
import de.garrafao.phitag.domain.joblisting.error.JoblistingException;
import de.garrafao.phitag.domain.joblisting.error.JoblistingNotFoundException;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Service
public class JoblistingApplicationService {

    // Repository

    private final JoblistingRepository joblistingRepository;

    private final AnnotatorRepository annotatorRepository;

    // Services

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public JoblistingApplicationService(
            final JoblistingRepository joblistingRepository,
            final AnnotatorRepository annotatorRepository,

            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,

            final CommonService commonService,
            final ValidationService validationService) {
        this.joblistingRepository = joblistingRepository;
        this.annotatorRepository = annotatorRepository;

        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    // Getter
    /**
     * Returns a list of all joblistings based on the given query.
     * 
     * @param query the query
     * @return a list of all {@link JoblistingDto} based on the given query
     */
    public List<JoblistingDto> queryJoblistingDto(final Query query) {
        return joblistingRepository.findByQuery(query).stream().map(JoblistingDto::from).toList();
    }

    /**
     * Returns a list of all joblistings based on the given query, where the user is
     * the owner of the project.
     * 
     * NOTE: Find a better way to do this. Merge with queryJoblistingDto?
     * TODO: Extend to let annotators with admin rights to also see joblistings
     *
     * @param query the query
     * @return a list of all {@link PersonalJoblistingDto} based on the given query
     */
    public List<PersonalJoblistingDto> queryPersonalJoblistingDto(final Query query) {
        return joblistingRepository.findByQuery(query).stream().map(PersonalJoblistingDto::from).toList();
    }

    // Setter
    /**
     * Creates a new joblisting based on the given command.
     * 
     * @param authenticationToken
     * @param command
     */
    @Transactional
    public void create(final String authenticationToken, final CreateJoblistingCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project project = this.commonService.getProject(command.getOwner(), command.getProject());

        this.validationService.projectAdminAccess(requester, project);
        this.validateCreateJoblistingCommand(command);

        this.joblistingRepository
                .save(new Joblisting(command.getName(), project,command.getPhase(), command.isOpen(), command.getDescription()));
    }

    /**
     * Joins a joblisting based on the given command.
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @Transactional
    public void join(final String authenticationToken, final JoinJoblistingCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project project = this.commonService.getProject(command.getOwner(), command.getProject());

        final Joblisting joblisting = this.joblistingRepository
                .findByIdNameAndIdProjectidNameAndIdProjectidOwnername(command.getName(), command.getProject(),
                        command.getOwner())
                .orElseThrow(JoblistingNotFoundException::new);

        if (!joblisting.isActive()) {
            throw new JoblistingException("Joblisting is not active.");
        }

        if (joblisting.getWaitinglist().contains(requester)) {
            throw new JoblistingException("User is already on waitinglist");
        }

        if (this.commonService.isUserAnnotator(project.getId().getOwnername(), project.getId().getName(),
                requester.getUsername())) {
            throw new JoblistingException("User is already an annotator");
        }

        if (joblisting.isOpen()) {
            Entitlement entitlement = this.commonService.getEntitlement(EntitlementEnum.ENTITLEMENT_USER.name());
            Annotator annotator = this.annotatorRepository.save(new Annotator(requester, project, entitlement));
            this.annotatorStatisticApplicationService.initializeAnnotatorStatistic(annotator);
        } else {
            joblisting.getWaitinglist().add(requester);
        }
    }

    /**
     * Add users from the waitinglist to the project based on the given command.
     * 
     * TODO: Add validation of command as precondition?
     * 
     * @param authenticationToken the authentication token
     * @param command             the command
     */
    @Transactional
    public void add(final String authenticationToken, final AddUsersFromWaitinglistCommand command) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project project = this.commonService.getProject(command.getOwner(), command.getProject());

        if (!project.getOwner().equals(requester)) {
            throw new JoblistingException("User is not the owner of the project");
        }

        final Joblisting joblisting = this.joblistingRepository
                .findByIdNameAndIdProjectidNameAndIdProjectidOwnername(command.getName(), command.getProject(),
                        command.getOwner())
                .orElseThrow(JoblistingNotFoundException::new);

        if (!joblisting.isActive()) {
            throw new JoblistingException("Joblisting is not active.");
        }

        // Add users and remove from waitinglist
        final Entitlement entitlement = this.commonService.getEntitlement(EntitlementEnum.ENTITLEMENT_USER.name());
        command.getUsers().forEach(username -> {
            final User userToAdd = this.commonService.getUser(username);
            if (joblisting.getWaitinglist().contains(userToAdd) &&
                    !this.commonService.isUserAnnotator(project.getId().getOwnername(), project.getId().getName(),
                            userToAdd.getUsername())) {
                joblisting.getWaitinglist().remove(userToAdd);
                Annotator annotator = this.annotatorRepository.save(new Annotator(userToAdd, project, entitlement));
                this.annotatorStatisticApplicationService.initializeAnnotatorStatistic(annotator);

            }
        });

    }

    // Verifier

    private void validateCreateJoblistingCommand(final CreateJoblistingCommand command) {
        // Verify name
        this.validationService.name(command.getName());

        if (this.joblistingRepository.findByIdNameAndIdProjectidNameAndIdProjectidOwnername(command.getName(),
                command.getProject(), command.getOwner()).isPresent()) {
            throw new JoblistingAlreadyExistsException();
        }

    }

}
