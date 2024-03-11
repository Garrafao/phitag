package de.garrafao.phitag.application.phase;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.InstanceApplicationService;
import de.garrafao.phitag.application.judgement.JudgementApplicationService;
import de.garrafao.phitag.application.phase.data.*;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.phasestatistic.PhaseStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.helper.Pair;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.PhaseRepository;
import de.garrafao.phitag.domain.phase.data.PhaseStatusEnum;
import de.garrafao.phitag.domain.phase.error.*;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.sampling.Sampling;
import de.garrafao.phitag.domain.statistic.statisticannotationmeasure.StatisticAnnotationMeasure;
import de.garrafao.phitag.domain.statistic.tutorialannotationmeasurehistory.TutorialAnnotationMeasureHistoryRepository;
import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.status.TaskStatusEnum;
import de.garrafao.phitag.domain.tasks.Task;
import de.garrafao.phitag.domain.tasks.TaskRepository;
import de.garrafao.phitag.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhaseApplicationService {

    // Repository

    private final PhaseRepository phaseRepository;

    private final TaskRepository taskRepository;

    private final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository;

    // Services

    private final JudgementApplicationService judgementApplicationService;

    private final InstanceApplicationService instanceApplicationService;

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    private final PhaseStatisticApplicationService phaseStatisticApplicationService;


    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public PhaseApplicationService(
            final PhaseRepository phaseRepository,
            final TaskRepository taskRepository,
            final TutorialAnnotationMeasureHistoryRepository tutorialAnnotationMeasureHistoryRepository,

            final JudgementApplicationService judgementApplicationService,
            final InstanceApplicationService instanceApplicationService,
            final UserStatisticApplicationService userStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,
            final PhaseStatisticApplicationService phaseStatisticApplicationService,

            final CommonService commonService, final ValidationService validationService) {
        this.phaseRepository = phaseRepository;
        this.taskRepository = taskRepository;
        this.tutorialAnnotationMeasureHistoryRepository = tutorialAnnotationMeasureHistoryRepository;
        this.judgementApplicationService = judgementApplicationService;
        this.instanceApplicationService = instanceApplicationService;

        this.userStatisticApplicationService = userStatisticApplicationService;
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;
        this.phaseStatisticApplicationService = phaseStatisticApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    // API methods

    // Getters

    /**
     * Returns a list of all phases of a project.
     * 
     * @param authenticationToken The authentication token of the user.
     * @param owner               The owner of the project.
     * @param project             The name of the project.
     * @param query               The query to filter the phases.
     * @return A list of all {@PhaseDto} of a project.
     */
    public List<PhaseDto> queryPhaseDtos(final String authenticationToken, final String owner, final String project,
            final Query query) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        if (this.commonService.isUserAnnotator(owner, project, requester.getUsername())) {
            final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());
            return this.phaseRepository.findByQuery(query).stream()
                    .map(phase -> PhaseDto.from(phase, tutorialProgressOfPhase(annotator, phase)))
                    .collect(Collectors.toList());
        }
        return this.phaseRepository.findByQuery(query).stream().map(PhaseDto::from).collect(Collectors.toList());
    }

    /**
     * Returns a specific phase of a project.
     * 
     * @param authenticationToken The authentication token of the user.
     * @param owner               The owner of the project.
     * @param project             The name of the project.
     * @param phase               The name of the phase.
     * @return The {@PhaseDto} of the phase.
     */
    public PhaseDto getPhaseDto(final String authenticationToken, final String owner, final String project,
            final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, projectEntity);

        if (this.commonService.isUserAnnotator(owner, project, requester.getUsername())) {
            final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());
            return PhaseDto.from(phaseEntity, tutorialProgressOfPhase(annotator, phaseEntity));
        }

        return PhaseDto.from(phaseEntity);
    }

    /**
     * Return if requesting user has access to annotate the phase.
     * 
     * @param authenticationToken The authentication token of the user.
     * @param owner               The owner of the project.
     * @param project             The name of the project.
     * @param phase               The name of the phase.
     * @return True if user has access to annotate the phase.
     */
    public boolean hasAccessToAnnotate(final String authenticationToken, final String owner, final String project,
            final String phase) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

            this.validationService.phaseAnnotationAccess(requester, phaseEntity);

        return true;
    }

    /**
     * Return tutorial measure history of a phase.
     * 
     * @param authenticationToken
     * @param owner               The owner of the project.
     * @param project             The name of the project.
     * @param phase               The name of the phase.
     */
    public List<TutorialHistoryDto> getTutorialMeasureHistory(final String authenticationToken, final String owner,
            final String project,
            final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.phaseAnnotationAccess(requester, phaseEntity);

        return this.tutorialAnnotationMeasureHistoryRepository.findByIdPhaseid(phaseEntity.getId()).stream()
                .map(TutorialHistoryDto::from).collect(Collectors.toList());

    }

    // Setters

    /**
     * Creates a new phase.
     * 
     * @param authenticationToken The authentication token of the user.
     * @param command             The command to create the phase.
     */
    @Transactional
    public void create(final String authenticationToken, final CreatePhaseCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(command.getOwner(), command.getProject());
        final AnnotationType annotationType = this.commonService.getAnnotationType(command.getAnnotationType());
        final Sampling sampling = this.commonService.getSampling(command.getSampling());

        final StatisticAnnotationMeasure statisticAnnotationMeasure;
        final Double annotationAgreement;

        if (command.isTutorial()) {
            statisticAnnotationMeasure = this.commonService
                    .getStatisticAnnotationMeasure(command.getAnnotationAgreement());
            annotationAgreement = command.getThreshold();
        } else {
            statisticAnnotationMeasure = null;
            annotationAgreement = 0.0;
        }

        this.validationService.projectAdminAccess(requester, projectEntity);
        validateCreateCommand(command);

        final Phase entity = this.phaseRepository
                .save(new Phase(command.getName(),
                        projectEntity,
                        annotationType,
                        sampling,
                        command.getDescription(),
                        command.getTaskhead(),
                        command.getInstancePerSample(),
                        command.isTutorial(),
                        statisticAnnotationMeasure,
                        annotationAgreement));

        // Update UserStatistics for AnnotationType
        this.userStatisticApplicationService.updatePhaseRelatedStatistics(entity);

        // Create PhaseStatistics for this phase
        this.phaseStatisticApplicationService.initializePhaseStatistic(entity);

    }
    @Transactional
    public void deletePhase(final String authenticationToken, final String owner,
                            final String project, final String phase) {

        final User user = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        this.validationService.projectAdminAccess(user, projectEntity);

        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);
        this.phaseRepository.delete(phaseEntity);


    }

    @Transactional
    public void createCode(final String authenticationToken, final String owner, final String project,
                           final String phase, final String code) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);
        this.validationService.projectAccess(requester, projectEntity);

        if(phaseEntity!=null){
            phaseEntity.setCode(code);
        }
    }

    /**
     * Close Phase
     * 
     * @param authenticationToken The authentication token of the user.
     * @param owner               The owner of the project.
     * @param project             The name of the project.
     * @param phase               The name of the phase.
     */
    @Transactional
    public void updatePhaseStatus(final String authenticationToken, final String owner, final String project,
            final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(requester, projectEntity);

        if (phaseEntity.isTutorial()) {
            throw new TutorialException("Tutorial phase can not be closed.");
        }

        if(phaseEntity.getStatus().equals(PhaseStatusEnum.CLOSED.name())){
            phaseEntity.setStatus(PhaseStatusEnum.OPEN);
        }
        else {
            phaseEntity.setStatus(PhaseStatusEnum.CLOSED);
        }
        this.phaseRepository.save(phaseEntity);
        // create a task to update the phase statistics
        this.phaseStatisticApplicationService.createPhaseStatisticTask(phaseEntity);

        // create a task to update all annotator statistics for this phase
        for (final Annotator annotator : this.commonService.getAnnotatorsOfProject(owner, project)) {
            this.annotatorStatisticApplicationService.createAnnotatorStatisticTask(annotator);
        }

        /*
        if (phaseEntity.getStatus().equals(PhaseStatusEnum.CLOSED.name())) {
            throw new PhaseUpdateException("Phase is already closed.");
        }
         **/

    }

    /**
     * Add requirements to a phase.
     * 
     * @param authenticationToken The authentication token of the user.
     * @param command             The command to add requirements to the phase.
     */
    @Transactional
    public void addRequirements(final String authenticationToken, final AddRequirementsCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(command.getOwner(), command.getProject());
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        this.validationService.projectAdminAccess(requester, projectEntity);
        if (phaseEntity.isTutorial()) {
            throw new TutorialException("Tutorial phase cannot have requirements");
        }

        final List<Phase> tutorials = new ArrayList<>();
        command.getTutorials().forEach(tutorial -> {
            final Phase tutorialPhase = this.commonService.getPhase(command.getOwner(), command.getProject(), tutorial);
            if (!tutorialPhase.isTutorial()) {
                throw new TutorialException("Phase " + tutorial + " is not a tutorial");
            }
            tutorials.add(tutorialPhase);
        });

        phaseEntity.setTutorialRequirements(tutorials);
        this.phaseRepository.save(phaseEntity);
    }

    /**
     * Delete requirements from a phase.
     *
     * @param authenticationToken The authentication token of the user.
     * @param owner   owner of the project.
     * @param project   name of the project.
     * @param phase   name of the project.
     * @param requirements   requirements to delete
     */
    @Transactional
    public void deleteRequirements(final String authenticationToken, final String owner,
                                   final String project, final String phase, final String requirements) {
        final User user = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(user, projectEntity);

        // Get the current tutorial requirements
        final List<Phase> currentTutorials = phaseEntity.getTutorialRequirements();

        // Use a single removeIf statement to remove tutorials based on their names
        currentTutorials.removeIf(tutorialPhase -> requirements.contains(tutorialPhase.getDisplayname()));

        // Update the phase entity with the modified list of tutorial requirements
        phaseEntity.setTutorialRequirements(currentTutorials);

        // Save the updated phase entity
        this.phaseRepository.save(phaseEntity);
    }

    /**
     * Start computational annotation for a specific project.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param command             The command to start computational annotation
     */
    public void startComputationalAnnotation(String authenticationToken, StartComputationalAnnotationCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        validateComputationalCommand(command);

        final List<Annotator> bots = new ArrayList<>();

        command.getAnnotators().stream().map(annotator -> this.commonService.getAnnotator(command.getOwner(),
                command.getProject(), annotator)).forEach(bots::add);

        final Status pending = this.commonService.getStatus(TaskStatusEnum.TASK_PENDING.name());
        for (Annotator bot : bots) {
            this.taskRepository.save(new Task(bot.getUser(), requester, phaseEntity, pending));
        }

    }

    private void validateCreateCommand(final CreatePhaseCommand command) {
        if (this.phaseRepository.findByIdNameAndIdProjectidNameAndIdProjectidOwnername(command.getName(),
                command.getProject(), command.getOwner()).isPresent()) {
            throw new PhaseAlreadyExistsException();
        }

        this.validationService.name(command.getName());

        if (PhaseNameRestrictionEnum.contains(command.getName().toLowerCase())) {
            throw new PhaseNameRestrictionException();
        }

        if (command.isTutorial()) {
            if (command.getAnnotationAgreement().isBlank()) {
                throw new PhaseUpdateException("Annotation agreement must not be empty");
            }

            commonService.getStatisticAnnotationMeasure(command.getAnnotationAgreement());

        }
    }

    /**
     * Returns a list of Pairs containing the name of the tutorial (phase) and if
     * the user has already finished the tutorial.
     * 
     * @param annotator The annotator.
     * @param phase     The phase.
     * 
     * @return A list of Pairs containing the name of the tutorial (phase) and if
     *         the user has already finished the tutorial.
     */
    private List<Pair<String, Boolean>> tutorialProgressOfPhase(final Annotator annotator, final Phase phase) {
        final List<Pair<String, Boolean>> tutorialProgress = new ArrayList<>();

        if (phase.isTutorial()) {
            tutorialProgress
                    .add(new Pair<>(phase.getId().getName(), annotator.getCompletedTutorials().contains(phase)));
            return tutorialProgress;
        }

        phase.getTutorialRequirements().forEach(tutorial -> {
            tutorialProgress
                    .add(new Pair<>(tutorial.getId().getName(), annotator.getCompletedTutorials().contains(tutorial)));
        });

        return tutorialProgress;
    }

    private void validateComputationalCommand(final StartComputationalAnnotationCommand command) {
        if (command.getAnnotators().isEmpty()) {
            throw new ComputationalAnnotationException("Must have at least one computational annotator");
        }

        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        if (phaseEntity.isTutorial()) {
            throw new ComputationalAnnotationException("Cannot start computational annotation for tutorial phase");
        }

        for (String annotator : command.getAnnotators()) {
            final Annotator annotatorEntity = this.commonService.getAnnotator(command.getOwner(), command.getProject(),
                    annotator);
            if (!annotatorEntity.getUser().isIsbot()) {
                throw new ComputationalAnnotationException("Annotator " + annotator + " is not a bot");
            }
        }
    }

}
