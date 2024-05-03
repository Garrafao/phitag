package de.garrafao.phitag.integration;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.project.data.UpdateProjectCommand;
import de.garrafao.phitag.application.user.data.UpdateUserCommand;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.project.ProjectId;
import de.garrafao.phitag.domain.sampling.SamplingRepository;
import de.garrafao.phitag.domain.user.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import de.garrafao.phitag.application.authentication.AuthenticationApplicationService;
import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import de.garrafao.phitag.application.project.ProjectApplicationService;
import de.garrafao.phitag.application.project.data.CreateProjectCommand;
import de.garrafao.phitag.application.project.data.ProjectDto;
import de.garrafao.phitag.domain.annotator.AnnotatorRepository;
import de.garrafao.phitag.domain.annotator.query.AnnotatorQueryBuilder;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.project.ProjectRepository;
import de.garrafao.phitag.domain.project.error.ProjectExistException;
import de.garrafao.phitag.domain.project.error.ProjectNotExistsException;
import de.garrafao.phitag.domain.project.query.ProjectQueryBuilder;
import de.garrafao.phitag.domain.statistic.annotatorstatistic.AnnotatorStatisticRepository;
import de.garrafao.phitag.domain.statistic.projectstatistic.ProjectStatisticRepository;
import de.garrafao.phitag.domain.statistic.userstatistic.UserStatisticRepository;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("integration")
public class ProjectApplicationServiceIT {

    @Autowired
    private AuthenticationApplicationService authenticationApplicationService;

    @Autowired
    private ProjectApplicationService projectApplicationService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AnnotatorRepository annotatorRepository;

    @Autowired
    private UserStatisticRepository userStatisticRepository;

    @Autowired
    private ProjectStatisticRepository projectStatisticRepository;

    @Autowired
    private AnnotatorStatisticRepository annotatorStatisticRepository;

    @Autowired
    private CommonService commonService;

    private static String jwtToken;

    /**
     * Before all tests, "log in" as "user-0" with password "Password1234!" and get
     * the JWT token.
     */
    @BeforeEach
    public void before() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-0", "Password1234!");
        ProjectApplicationServiceIT.jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

    }

    // Query

    @Test
    @Transactional
    public void it_query_normal_path() {
        final Query query = new ProjectQueryBuilder()
                .withName("project-0")
                .build();

        List<ProjectDto> projectDtos = projectApplicationService.queryProjectDto(query);
        assertEquals(1, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_query_empty_return_all() {
        final Query query = new ProjectQueryBuilder()
                .build();

        List<ProjectDto> projectDtos = projectApplicationService.queryProjectDto(query);
        assertEquals(2, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_query_empty_return_none() {
        final Query query = new ProjectQueryBuilder()
                .withName("project")
                .build();

        List<ProjectDto> projectDtos = projectApplicationService.queryProjectDto(query);
        assertEquals(0, projectDtos.size());
    }

    // getPublicProjectOfUserDtos

    @Test
    @Transactional
    public void it_getPublicProjectOfUserDtos_normal_path_owner() {
        List<ProjectDto> projectDtos = projectApplicationService
                .getPublicProjectOfUserDtos(ProjectApplicationServiceIT.jwtToken, "user-0");
        assertEquals(2, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_getPublicProjectOfUserDtos_normal_path_non_owner() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-1", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        List<ProjectDto> projectDtos = projectApplicationService.getPublicProjectOfUserDtos(jwtToken, "user-0");
        assertEquals(1, projectDtos.size());
    }

    // findProjectOfUserAsDto

    @Test
    @Transactional
    public void it_getPublicProjectOfUserDtos_normal_path() {
        ProjectDto projectDto = projectApplicationService.findProjectOfUserAsDto(ProjectApplicationServiceIT.jwtToken,
                "user-0", "project-0");
        assertEquals("project-0", projectDto.getId().getName());
        assertEquals("user-0", projectDto.getId().getOwner());
    }

    @Test
    @Transactional
    public void it_getPublicProjectOfUserDtos_owner_of_private() {
        ProjectDto projectDto = projectApplicationService.findProjectOfUserAsDto(ProjectApplicationServiceIT.jwtToken,
                "user-0", "project-1");
        assertEquals("project-1", projectDto.getId().getName());
        assertEquals("user-0", projectDto.getId().getOwner());
    }

    @Test
    @Transactional
    public void it_getPublicProjectOfUserDtos_non_owner_of_private_throws() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-1", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        assertThrows(AccessDenidedException.class, () -> {
            projectApplicationService.findProjectOfUserAsDto(jwtToken, "user-0", "project-1");
        });
    }

    // getPersonalProjectDtos

    @Test
    @Transactional
    public void it_getPersonalProjectDtos_normal_path() {
        List<ProjectDto> projectDtos = projectApplicationService
                .getPersonalProjectDtos(ProjectApplicationServiceIT.jwtToken, "user-0");
        assertEquals(2, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_getPersonalProjectDtos_own_without_projects_empty() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-1", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        assertEquals(0, projectApplicationService.getPersonalProjectDtos(jwtToken, "").size());
    }

    @Test
    @Transactional
    public void it_getPersonalProjectDtos_non_owner_different_user_empty() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-1", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        assertEquals(0, projectApplicationService.getPersonalProjectDtos(jwtToken, "").size());
    }

    // getAnnotatorProjectDtos

    @Test
    @Transactional
    public void it_getAnnotatorProjectDtos_owner() {
        List<ProjectDto> projectDtos = projectApplicationService
                .getAnnotatorProjectDtos(ProjectApplicationServiceIT.jwtToken, "user-0");
        assertEquals(2, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_getAnnotatorProjectDtos_only_annotator() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-1", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        List<ProjectDto> projectDtos = projectApplicationService.getAnnotatorProjectDtos(jwtToken, "");
        assertEquals(1, projectDtos.size());
    }

    @Test
    @Transactional
    public void it_getAnnotatorProjectDtos_none_empty() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-2", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        List<ProjectDto> projectDtos = projectApplicationService.getAnnotatorProjectDtos(jwtToken, "");
        assertEquals(0, projectDtos.size());
    }

    // create

    @Test
    @Transactional
    public void it_create_normal_path() {
        CreateProjectCommand projectDto = new CreateProjectCommand("project-2", "VISIBILITY_PUBLIC", "German",
                "description");
        projectApplicationService.create(ProjectApplicationServiceIT.jwtToken, projectDto);

        var opProject = projectRepository.findByIdNameAndIdOwnername("project-2", "user-0");

        var annotatorQuery = new AnnotatorQueryBuilder()
                .withProject("project-2")
                .build();

        var annotators = annotatorRepository.findByQuery(annotatorQuery);
        var userStatistic = userStatisticRepository.findByUsername("user-0");

        assertTrue(opProject.isPresent());
        assertEquals("project-2", opProject.get().getId().getName());
        assertEquals("user-0", opProject.get().getId().getOwnername());

        assertEquals(1, annotators.size());
        assertEquals("user-0", annotators.get(0).getId().getUsername());

        assertTrue(userStatistic.isPresent());
        assertEquals(Integer.valueOf(3), userStatistic.get().getProjectcount());
    }

    @Test
    @Transactional
    public void it_create_project_exists_throws() {
        CreateProjectCommand projectDto = new CreateProjectCommand("project-0", "VISIBILITY_PUBLIC", "German",
                "description");


        assertThrows(ProjectExistException.class, () -> {
            projectApplicationService.create(ProjectApplicationServiceIT.jwtToken, projectDto);
        });
    }


//update
    @Test
    @Transactional
    public void it_update_project() {
        // Arrange
        UpdateProjectCommand command = new UpdateProjectCommand("test_update",
                "test_update_name", "German", "VISIBILITY_PUBLIC", false);

        final String project = "project-0";
        final String owner = "user-0";
        projectApplicationService.update(ProjectApplicationServiceIT.jwtToken, project, owner, command);

        ProjectDto projectDto = projectApplicationService.findProjectOfUserAsDto(ProjectApplicationServiceIT.jwtToken,
                "user-0", "project-0");

        // Assert
        assertEquals("test_update", projectDto.getDescription());
        assertEquals("test_update_name", projectDto.getDisplayname());
        assertEquals("German", projectDto.getLanguage().getName());
        assertEquals("VISIBILITY_PUBLIC", projectDto.getVisibility().getName());
        assertEquals(false, projectDto.getActive()); // Use assertFalse for a boolean
    }



    // delete

    @Test
    @Transactional
    public void it_delete_project_exists() {
        projectApplicationService.delete(ProjectApplicationServiceIT.jwtToken, "project-0");

        var opProject = projectRepository.findByIdNameAndIdOwnername("project-0", "user-0");
        var annotatorQuery = new AnnotatorQueryBuilder()
                .withProject("project-0")
                .build();

        var annotators = annotatorRepository.findByQuery(annotatorQuery);
        var userStatistic = userStatisticRepository.findByUsername("user-0");

        assertTrue(opProject.isEmpty());
        assertEquals(0, annotators.size());

        assertTrue(userStatistic.isPresent());
        assertEquals(Integer.valueOf(2), userStatistic.get().getProjectcount());
    }

    @Test
    @Transactional
    public void it_delete_project_not_exists() {
        assertThrows(ProjectNotExistsException.class, () -> {
            projectApplicationService.delete(ProjectApplicationServiceIT.jwtToken, "project-2");
        });
    }

    @Test
    @Transactional
    public void it_delete_project_not_owner() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-2", "Password1234!");
        String jwtToken = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

        assertThrows(ProjectNotExistsException.class, () -> {
            projectApplicationService.delete(jwtToken, "project-0");
        });
    }
}
