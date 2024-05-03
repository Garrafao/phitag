package de.garrafao.phitag.application.project;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.entitlement.data.EntitlementEnum;
import de.garrafao.phitag.application.project.data.CreateProjectCommand;
import de.garrafao.phitag.application.project.data.ProjectDto;
import de.garrafao.phitag.application.project.data.ProjectNameRestrictionEnum;
import de.garrafao.phitag.application.project.data.UpdateProjectCommand;
import de.garrafao.phitag.application.statistics.annotatostatistic.AnnotatorStatisticApplicationService;
import de.garrafao.phitag.application.statistics.projectstatic.ProjectStatisticApplicationService;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.annotator.query.AnnotatorQueryBuilder;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.entitlement.Entitlement;
import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.project.ProjectRepository;
import de.garrafao.phitag.domain.project.error.ProjectExistException;
import de.garrafao.phitag.domain.project.error.ProjectNameRestrictionException;
import de.garrafao.phitag.domain.project.query.ProjectQueryBuilder;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.visibility.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectApplicationService {

    // Repository

    private final ProjectRepository projectRepository;

    private final AnnotatorRepository annotatorRepository;

    // Services

    private final UserStatisticApplicationService userStatisticApplicationService;

    private final ProjectStatisticApplicationService projectStatisticApplicationService;

    private final AnnotatorStatisticApplicationService annotatorStatisticApplicationService;

    // Common

    private final CommonService commonService;

    private final ValidationService validationService;


    // Other

    @Autowired
    public ProjectApplicationService(
            final ProjectRepository projectRepository,
            final AnnotatorRepository annotatorRepository,

            final UserStatisticApplicationService userStatisticApplicationService,
            final ProjectStatisticApplicationService projectStatisticApplicationService,
            final AnnotatorStatisticApplicationService annotatorStatisticApplicationService,

            final CommonService commonService,
            final ValidationService validationService) {
        this.projectRepository = projectRepository;
        this.annotatorRepository = annotatorRepository;

        this.userStatisticApplicationService = userStatisticApplicationService;
        this.projectStatisticApplicationService = projectStatisticApplicationService;
        this.annotatorStatisticApplicationService = annotatorStatisticApplicationService;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    // API methods

    // Getters

    /**
     * Get projects by query with fuzzy search.
     * 
     * @param query query for search
     * @return list of {@ProjectDto}
     */
    public List<ProjectDto> queryProjectDto(final Query query) {
        return this.projectRepository.findByQuery(query).stream().map(ProjectDto::from).toList();
    }

    /**
     * Get public projects of a user
     * 
     * @param authenticationToken fetch user information from authentication token
     * @param owner owner of the project
     * @return list of {@ProjectDto}
     */
    public List<ProjectDto> getPublicProjectOfUserDtos(final String authenticationToken, final String owner) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        String visibility = VisibilityEnum.VISIBILITY_PUBLIC.name();
        if (requester.getUsername().equals(owner)) {
            visibility = "";
        }

        final Query query = new ProjectQueryBuilder()
                .withOwner(owner)
                .withVisibility(visibility)
                .build();

        return this.projectRepository.findByQuery(query).stream().map(ProjectDto::from).toList();
    }

    /**
     * Get a project by its id.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @return list of {@ProjectDto}
     */
    public ProjectDto findProjectOfUserAsDto(final String authenticationToken, final String owner,
            final String project) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(owner, project);

        this.validationService.projectAccess(requester, projectEntity);

        return ProjectDto.from(projectEntity);
    }

    /**
     * Get a projects of requesting user, where the requesting user is the owner.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param querystring         the query string
     * @return list of {@ProjectDto}
     */
    public List<ProjectDto> getPersonalProjectDtos(final String authenticationToken, final String querystring) {
        final Query query = new ProjectQueryBuilder()
                .withFuzzySearch(querystring)
                .withOwner(this.commonService.getUsernameFromAuthenticationToken(authenticationToken))
                .build();
        return this.projectRepository.findByQuery(query).stream().map(ProjectDto::from).toList();
    }

    /**
     * Get a projects of requesting user, where the requesting user is an annotator.
     *
     * @param authenticationToken the authentication token of the requesting user
     * @param querystring         the query string
     * @return list of {@ProjectDto}
     */
    public List<ProjectDto> getAnnotatorProjectDtos(final String authenticationToken, final String querystring) {
        final Query query = new AnnotatorQueryBuilder()
                .withFuzzySearch(querystring)
                .withUser(this.commonService.getUsernameFromAuthenticationToken(authenticationToken))
                .build();

        return this.annotatorRepository.findByQuery(query).stream().map(annotator -> {
            return ProjectDto.from(annotator.getProject());
        }).toList();
    }

    // Setters
    /**
     * Create a new project.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param command             the command
     */
    @Transactional
    public void create(final String authenticationToken, final CreateProjectCommand command) {
        validateCreateCommand(authenticationToken, command);

        // Create project
        User owner = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Visibility visibility = this.commonService.getVisibility(command.getVisibility());
        Language language = this.commonService.getLanguage(command.getLanguage());

        Project project = projectRepository
                .save(new Project(command.getName(), owner, visibility, language, command.getDescription()));

        // Add owner as annotator with admin entitlement
        Entitlement entitlement = this.commonService.getEntitlement(EntitlementEnum.ENTITLEMENT_ADMIN.name());
        Annotator annotator = this.annotatorRepository.save(new Annotator(owner, project, entitlement));

        // Update user related statistics
        this.userStatisticApplicationService.updateProjectRelatedStatistics(project);

        // Create project statistics
        this.projectStatisticApplicationService.initializeProjectStatistic(project);

        // Create annotator statistics
        this.annotatorStatisticApplicationService.initializeAnnotatorStatistic(annotator);
    }

    /**
     * Update a project.
     *
     * @param authenticationToken the authentication token of the requesting user
     * @param command fields which user want to update
     * @param owner  owner of the project
     * @param project name of the project which user owner want to update

     */

    @Transactional
    public void update(final String authenticationToken,final String project, final String owner, UpdateProjectCommand command){

        //fetching user information
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        //check project is exist or not
        final Project existingProject = this.commonService.getProject(owner, project);

        //checking user has right to  update a project or not
        this.validationService.projectAccess(requester, existingProject);

        //Update the project
       this.validateUpdateCommand(existingProject, command);
    }


    /**
     * Delete a project.
     * 
     * @param authenticationToken fetching user information form authentication token
     * @param  projectName name of project
     */
    @Transactional
    public void delete(final String authenticationToken, final String projectName) {
        User owner = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Project project = this.commonService.getProject(owner.getUsername(), projectName);

        try {
            this.validationService.projectOwnerAccess(owner, project);
        } catch (Exception e) {
            throw new AccessDenidedException("Only the owner of a project can delete it.");
        }
     /*   final List<Phase> phases = this.commonService.getPhasesOfProject(project);
        final List<Annotator> annotators = this.commonService.getAnnotatorsOfProject(owner.getUsername(), projectName);
        if (!phases.isEmpty() && !annotators.isEmpty()) {
            for (Phase phase : phases) {
                this.phaseApplicationService.deletePhase(authenticationToken, owner.getUsername(), project.getId().getName(),
                        phase.getDisplayname());
            }

            for (Annotator annotator : annotators) {
               this.annotatorRepository.delete(annotator);
            }
        }**/

        this.projectRepository.delete(project);
    }





    // Validators

    private void validateCreateCommand(final String authenticationToken, final CreateProjectCommand command) {
        User owner = this.commonService.getUserByAuthenticationToken(authenticationToken);

        this.validationService
                .name(command.getName())
                .visibility(command.getVisibility())
                .language(command.getLanguage());

        // validate uniqueness of user name + project name
        if (projectRepository.findByIdNameAndIdOwnername(command.getName(), owner.getUsername()).isPresent()) {
            throw new ProjectExistException();
        }

        if (ProjectNameRestrictionEnum.contains(command.getName().toLowerCase())) {
            throw new ProjectNameRestrictionException();
        }

    }

    private void validateUpdateCommand(final Project project, final UpdateProjectCommand command){
        if (command.getNewname() != null && !command.getNewname().isEmpty() && !command.getNewname().isBlank()) {
            project.setDisplayname(command.getNewname());
        }
        if (command.getNewdescription() != null && !command.getNewdescription().isEmpty() && !command.getNewdescription().isBlank()) {
            project.setDescription(command.getNewdescription());
        }
        if(command.getLanguage() != null && !command.getLanguage().isEmpty() && !command.getLanguage().isBlank()){
            Language language = this.commonService.getLanguage(command.getLanguage());
            project.setLanguage(language);
        }
        if(command.getVisibility() !=null && !command.getVisibility().isEmpty() && !command.getVisibility().isBlank()){
            Visibility visibility = this.commonService.getVisibility(command.getVisibility());
            project.setVisibility(visibility);
        }
        if (command.getActive() != null) {
            project.setActive(command.getActive());
        }

    }

}
