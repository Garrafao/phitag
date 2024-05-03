package de.garrafao.phitag.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import de.garrafao.phitag.application.authentication.AuthenticationApplicationService;
import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import de.garrafao.phitag.application.role.data.RoleEnum;
import de.garrafao.phitag.application.user.UserApplicationService;
import de.garrafao.phitag.application.user.data.CreateUserCommand;
import de.garrafao.phitag.application.user.data.UpdateUserCommand;
import de.garrafao.phitag.application.user.data.UserDataDto;
import de.garrafao.phitag.application.user.data.UserDto;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.user.UserRepository;
import de.garrafao.phitag.domain.user.error.InvalidPasswordException;
import de.garrafao.phitag.domain.user.error.UserExistsException;
import de.garrafao.phitag.domain.user.error.UserNotExistsException;
import de.garrafao.phitag.domain.user.query.UserQueryBuilder;
import io.jsonwebtoken.MalformedJwtException;

// @RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("integration")
public class UserApplicationServiceIT {

    @Autowired
    private AuthenticationApplicationService authenticationApplicationService;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private UserRepository userRepository;

    private static String jwtToken;

    /**
     * Before all tests, "log in" as "user-0" with password "Password1234!" and get
     * the JWT token.
     */
    @BeforeEach
    public void before() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-0", "Password1234!");
        UserApplicationServiceIT.jwtToken = authenticationApplicationService.authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();

    }

    // Query

    @Test
    @Transactional
    public void it_query_normal_path() {
        final Query query = new UserQueryBuilder()
                .withEmail("user-0@phitag.com")
                .withUsername("user-0")
                .withRole(RoleEnum.ROLE_ADMIN.name())
                .build();

        final Optional<UserDto> user = userApplicationService.queryUserDto(query).stream().findFirst();
        assertTrue(user.isPresent());
        assertEquals("user-0", user.get().getUsername());
    }

    @Test
    @Transactional
    public void it_query_all() {
        final Query query = new UserQueryBuilder()
                .build();

        final List<UserDto> user = userApplicationService.queryUserDto(query);
        assertEquals(5, user.size());
    }

    @Test
    @Transactional
    public void it_query_empty_path() {
        final Query query = new UserQueryBuilder()
                .withEmail("user-1@phitag.com")
                .withUsername("user-0")
                .build();

        final Optional<UserDto> user = userApplicationService.queryUserDto(query).stream().findFirst();
        assertTrue(!user.isPresent());
    }

    @Test
    @Transactional
    public void it_query_bot_empty_path() {
        final Query query = new UserQueryBuilder()
                .withEmail("user-0@phitag.com")
                .withUsername("user-0")
                .withRole(RoleEnum.ROLE_ADMIN.name())
                .bot(true)
                .build();

        final Optional<UserDto> user = userApplicationService.queryUserDto(query).stream().findFirst();
        assertTrue(user.isEmpty());
    }

    // findUserDtoByUsername

    @Test
    @Transactional
    public void it_find_user_self() {
        UserDto dto = userApplicationService.findUserDtoByUsername(UserApplicationServiceIT.jwtToken, "user-0");
        assertEquals("user-0", dto.getUsername());
    }

    @Test
    @Transactional
    public void it_find_user_different() {
        UserDto dto = userApplicationService.findUserDtoByUsername(UserApplicationServiceIT.jwtToken, "user-1");
        assertEquals("user-1", dto.getUsername());
    }

    @Test
    @Transactional
    public void it_find_disabled_user_throws_exception() {
        assertThrows(UserNotExistsException.class, () -> {
            userApplicationService.findUserDtoByUsername(UserApplicationServiceIT.jwtToken, "user-3");
        });
    }

    @Test
    @Transactional
    public void it_find_user_not_exists_throws_exception() {
        assertThrows(UserNotExistsException.class, () -> {
            userApplicationService.findUserDtoByUsername(UserApplicationServiceIT.jwtToken, "user-4");
        });
    }

    // getUserPersonalDataDto

    @Test
    @Transactional
    public void it_get_user_personal_data_self() {
        UserDataDto dto = userApplicationService.getUserPersonalDataDto(UserApplicationServiceIT.jwtToken);
        assertEquals("user-0", dto.getUsername());
    }

    @Test
    @Transactional
    public void it_get_user_personal_data_with_bad_token_throws_exception() {
        assertThrows(MalformedJwtException.class, () -> {
            userApplicationService.getUserPersonalDataDto("bad-token");
        });
    }

    // create

    @Test
    @Transactional
    public void it_create_user() {
        CreateUserCommand createUserCommand = new CreateUserCommand(
                "user-4", "user-4@phitag.com", "Password1234!", "USECASE_DEFAULT", Set.of("German"),"", true, true );

        userApplicationService.create(createUserCommand);

        User user = userRepository.findByUsername("user-4").get();

        assertEquals("user-4", user.getUsername());
        assertEquals("user-4@phitag.com", user.getEmail());
        assertEquals("USECASE_DEFAULT", user.getUsecase().getName());
    }

    @Test
    @Transactional
    public void it_create_user_with_bad_password_throws_exception() {
        assertThrows(
                InvalidPasswordException.class,
                () -> {
                    CreateUserCommand createUserCommand = new CreateUserCommand(
                            "user-5", "user-5@phitag.com", "password", "USECASE_DEFAULT", Set.of("German"), "", true, true);
                    userApplicationService.create(createUserCommand);
                }

        );
    }

    @Test
    @Transactional
    public void it_create_user_with_overlapping_username_throws_exception() {
        assertThrows(
                UserExistsException.class,
                () -> {
                    CreateUserCommand createUserCommand = new CreateUserCommand(
                            "user-0", "user--1@phitag.com", "Password1234!", "USECASE_DEFAULT", Set.of("German"), "",
                            true, true);
                    userApplicationService.create(createUserCommand);
                }

        );
    }

    @Test
    @Transactional
    public void it_create_user_with_overlapping_email_throws_exception() {
        assertThrows(
                UserExistsException.class,
                () -> {
                    CreateUserCommand createUserCommand = new CreateUserCommand(
                            "user--1", "user-1@phitag.com", "Password1234!", "USECASE_DEFAULT", Set.of("German"), "",
                            true, true);
                    userApplicationService.create(createUserCommand);
                }

        );
    }

    // update

    @Test
    @Transactional
    public void it_update_user() {
        UpdateUserCommand command = new UpdateUserCommand(
                "", "", "", "", "", null, "test", "Password1234!");

        userApplicationService.update(UserApplicationServiceIT.jwtToken, command);

        User user = userRepository.findByUsername("user-0").get();

        assertEquals("user-0", user.getUsername());
        assertEquals("test", user.getDescription());
    }

    @Test
    @Transactional
    public void it_update_user_with_username_update_has_no_effect() {
        UpdateUserCommand command = new UpdateUserCommand(
                "user---1", "", "", "", "", null, "test", "Password1234!");
        userApplicationService.update(UserApplicationServiceIT.jwtToken, command);
        User user = userRepository.findByUsername("user-0").get();

        assertEquals("user-0", user.getUsername());
        assertEquals("test", user.getDescription());
    }
}
