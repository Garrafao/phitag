package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.user.UserApplicationService;
import de.garrafao.phitag.application.user.data.CreateUserCommand;
import de.garrafao.phitag.application.user.data.UpdateUserCommand;
import de.garrafao.phitag.application.user.data.UserDataDto;
import de.garrafao.phitag.application.user.data.UserDto;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.user.query.UserQueryBuilder;
import de.garrafao.phitag.domain.visibility.Visibility;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserResource {

    private final UserApplicationService userApplicationService;

    @Autowired
    public UserResource(final UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * Returns a list of all users found by the given query values.
     * 
     * Only users with visibility {@link Visibility} VISIBILITY_PUBLIC and accounts
     * enabled are returned.
     * 
     * @param query
     *              the query to search for users
     * @return a list of users as DTOs
     */
    @GetMapping(path = "/query")
    public List<UserDto> query(
            @RequestParam(value = "query", required = false) final String query,
            @RequestParam(value = "bot", required = false) final Boolean bot) {
        final Query queryEnt = new UserQueryBuilder()
                .withFuzzySearch(query)
                .bot(bot)
                .withVisibility(VisibilityEnum.VISIBILITY_PUBLIC.name()).enabled(true).build();
        return userApplicationService.queryUserDto(queryEnt);
    }

    /**
     * Find a specific user by its username.
     * 
     * Only users with visibility {@link Visibility} VISIBILITY_PUBLIC and accounts
     * enabled can be found.
     * If the user is the account holder, the user is returned even if the account
     * is disabled and the visibility is not public.
     * 
     * @param authenticationToken
     *                            the authentication token of the account holder
     * @param username
     *                            the username of the user to find
     * @return
     *         the user as DTO
     */
    @GetMapping(path = "/find")
    public UserDto find(@RequestHeader(value = "Authorization") final String authenticationToken,
            @RequestParam(value = "username", required = false) final String username) {
        return userApplicationService.findUserDtoByUsername(authenticationToken, username);
    }

    /**
     * Get the personal data of the user, which contains more information than the
     * user DTO.
     * 
     * Only the account holder can get the personal data of the user, hence is
     * determined by the authentication token.
     * 
     * @param authenticationToken
     *                            the authentication token of the account holder
     * @return
     *         the personal data of the user as DTO {@link UserDataDto}
     */
    @GetMapping(path = "/personal")
    public UserDataDto personalData(@RequestHeader("Authorization") String authenticationToken) {
        return userApplicationService.getUserPersonalDataDto(authenticationToken);
    }

    /**
     * Create a new user. Used specificaly for the registration of a new user.
     * 
     * @param createUserCommand
     *                          the command to create a new user
     * @return
     *         the created user as DTO
     */
    @PostMapping(path = "/create")
    public void create(@RequestBody CreateUserCommand createUserCommand) {
        userApplicationService.create(createUserCommand);
    }

    /**
     * Update the user.
     * The updated user is determined by the authentication token.
     * 
     * @param authenticationToken
     * @param updateUserCommand
     */
    @PostMapping(path = "/update")
    public void update(@RequestHeader("Authorization") String authenticationToken,
            @RequestBody UpdateUserCommand updateUserCommand) {
        userApplicationService.update(authenticationToken, updateUserCommand);
    }

}
