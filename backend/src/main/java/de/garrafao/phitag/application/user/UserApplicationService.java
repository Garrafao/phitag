package de.garrafao.phitag.application.user;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.role.data.RoleEnum;
import de.garrafao.phitag.application.statistics.userstatistic.UserStatisticApplicationService;
import de.garrafao.phitag.application.user.data.*;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.role.Role;
import de.garrafao.phitag.domain.usecase.Usecase;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.user.UserRepository;
import de.garrafao.phitag.domain.user.error.*;
import de.garrafao.phitag.domain.user.query.UserQueryBuilder;
import de.garrafao.phitag.domain.visibility.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserApplicationService {

    // Repository
    private final UserRepository userRepository;

    // Services
    private final CommonService commonService;

    private final ValidationService validationService;

    private final UserStatisticApplicationService userStatisticApplicationService;

    // Other
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserApplicationService(
            final UserRepository userRepository,

            final CommonService commonService,
            final ValidationService validationService,
            final UserStatisticApplicationService userStatisticApplicationService,

            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.commonService = commonService;
        this.validationService = validationService;
        this.userStatisticApplicationService = userStatisticApplicationService;

        this.passwordEncoder = passwordEncoder;
    }

    // API methods

    // Getters

    /**
     * Returns a list of all users based on the given query.
     * 
     * @return A list of all users based on the given query.
     */
    public List<UserDto> queryUserDto(final Query query) {
        return this.userRepository.findByQuery(query).stream().map(UserDto::from).toList();
    }

    /**
     * Returns a user based on the given id.
     * 
     * @param authenticationToken The authentication token of the requesting user.
     * @param username            The username of the user to get.
     * @return A user based on the given id.
     */
    public UserDto findUserDtoByUsername(final String authenticationToken, final String username) {
        // Check if user is itself
        if (this.commonService.getUsernameFromAuthenticationToken(authenticationToken).equals(username)) {
            return UserDto.from(this.userRepository.findByUsername(username).orElseThrow(UserNotExistsException::new));
        }

        Query query = new UserQueryBuilder().withUsername(username)
                .withVisibility(VisibilityEnum.VISIBILITY_PUBLIC.name()).enabled(true).build();
        List<User> users = this.userRepository.findByQuery(query);


        if (users.size() != 1) {
            throw new UserNotExistsException();
        }

        return UserDto.from(users.get(0));
    }

    /**
     * Returns user data based on the token of the requesting user.
     * 
     * @param authenticationToken The authentication token of the requesting user.
     * @return User data based on the token of the requesting user.
     */
    public UserDataDto getUserPersonalDataDto(final String authenticationToken) {
        User user = this.commonService.getUserByAuthenticationToken(authenticationToken);
        return UserDataDto.from(user);
    }

    // Setters

    /**
     * Creates a new user.
     * 
     * @param command The command to create a new user.
     */
    @Transactional
    public void create(final CreateUserCommand command) {
        validateCreateCommand(command);

        final Set<Role> roles = new HashSet<>();
        if (command.getProlific_id() != null
                && !command.getProlific_id().isBlank()
                && !command.getProlific_id().isEmpty()) {
            roles.add(this.commonService.getRole(RoleEnum.ROLE_PROLIFIC.name()));
        } else {
            roles.add(this.commonService.getRole(RoleEnum.ROLE_USER.name()));

        }

        final Visibility visibility = this.commonService.getVisibility(VisibilityEnum.VISIBILITY_PUBLIC.name());
        final Usecase usecase = this.commonService.getUsecase(command.getUsecase());

        final Set<Language> languages = new HashSet<>();
        command.getLanguages().forEach(language -> languages.add(this.commonService.getLanguage(language)));

        final User entity = this.userRepository.save(new User(command.getUsername(),command.getEmail(),
                passwordEncoder.encode(command.getPassword()), roles, usecase, visibility, languages, command.getProlific_id()));

        this.userStatisticApplicationService.initializeUserStatistic(entity);
    }

    /**
     * Updates a user.
     * 
     * @param authenticationToken The authentication token of the requesting user.
     * @param command             The command to update a user.
     */
    @Transactional
    public void update(final String authenticationToken, final UpdateUserCommand command) {
        User user = this.commonService.getUserByAuthenticationToken(authenticationToken);
        this.validateUpdateCommand(user, command);

        // Update user
        this.updateUser(user, command);
    }

    // Validation methods

    private void validateCreateCommand(final CreateUserCommand command) {

        validationService.name(command.getUsername()).email(command.getEmail()).password(command.getPassword())
                .languageSet(command.getLanguages());

        if (!command.isPrivacypolicyAccepted()) {
            throw new PrivacyPolicyNotAcceptedException();
        }

        if (!command.isOfLegalAge()) {
            throw new NotOfLegalAgeException();
        }

        if (userRepository.findByUsername(command.getUsername()).isPresent()
                || userRepository.findByEmail(command.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        if (UsernameRestrictionEnum.contains(command.getUsername().toLowerCase())) {
            throw new UsernameRestrictionException();
        }

    }

    private void validateUpdateCommand(User user, final UpdateUserCommand command) {

        // Confirm Password Match
        if (!passwordEncoder.matches(command.getCurrentPassword(), user.getPassword())) {
            throw new UserNotExistsException();
        }

        validateUsername(command.getNewUsername());
        validateEmail(command.getNewEmail());
        validatePassword(command.getNewPassword());
        validateVisibility(command.getNewVisibility());
        validateLanguages(command.getNewLanguages());
        validateUsecase(command.getNewUsecase());

        // Description validation
        if (command.getNewDescription() == null) {
            throw new IllegalArgumentException();
        }
    }

    private void validateUsername(final String newUsername) {
        if (newUsername == null || newUsername.isEmpty() || newUsername.isBlank()) {
            return;
        }

        this.validationService.name(newUsername);

        if (this.userRepository.findByUsername(newUsername).isPresent()) {
            throw new UserExistsException();
        }
    }

    private void validateEmail(final String newEmail) {
        if (newEmail == null || newEmail.isEmpty() || newEmail.isBlank()) {
            return;
        }

        this.validationService.email(newEmail);

        if (this.userRepository.findByEmail(newEmail).isPresent()) {
            throw new UserExistsException();
        }
    }

    private void validatePassword(final String newPassword) {
        if (newPassword == null || newPassword.isEmpty() || newPassword.isBlank()) {
            return;
        }

        this.validationService.password(newPassword);
    }

    private void validateVisibility(final String newVisibility) {
        if (newVisibility == null || newVisibility.isEmpty() || newVisibility.isBlank()) {
            return;
        }

        this.validationService.visibility(newVisibility);
    }

    private void validateLanguages(final Set<String> newLanguages) {
        if (newLanguages == null || newLanguages.isEmpty()) {
            return;
        }

        this.validationService.languageSet(newLanguages);
    }

    private void validateUsecase(final String newUsecase) {
        if (newUsecase == null || newUsecase.isEmpty() || newUsecase.isBlank()) {
            return;
        }

        this.commonService.getUsecase(newUsecase);
    }

    // Helper methods

    private void updateUser(final User user, final UpdateUserCommand command) {
        // Only update if new value is not null, empty or blank
        // username will only update the display name
      //if (command.getNewUsername() != null && !command.getNewUsername().isEmpty()
            //    && !command.getNewUsername().isBlank()) {
            // TODO: What are the consequences of changing the username?
      //  }

        if (command.getNewEmail() != null && !command.getNewEmail().isEmpty() && !command.getNewEmail().isBlank()) {
            user.setEmail(command.getNewEmail());
        }

        if (command.getNewPassword() != null && !command.getNewPassword().isEmpty()
                && !command.getNewPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(command.getNewPassword()));
        }

        if (command.getNewVisibility() != null && !command.getNewVisibility().isEmpty()
                && !command.getNewVisibility().isBlank()) {
            user.setVisibility(this.commonService.getVisibility(command.getNewVisibility()));
        }

        if (command.getNewUsecase() != null && !command.getNewUsecase().isEmpty()
                && !command.getNewUsecase().isBlank()) {
            user.setUsecase(this.commonService.getUsecase(command.getNewUsecase()));
        }

        if (command.getNewLanguages() != null && !command.getNewLanguages().isEmpty()) {
            final Set<Language> languages = new HashSet<>();
            command.getNewLanguages()
                    .forEach(language -> languages.add(this.commonService.getLanguage(language)));
            user.setLanguages(languages);
        }

        if (command.getNewDescription() != null && !command.getNewDescription().isEmpty()
                && !command.getNewDescription().isBlank()) {
            user.setDescription(command.getNewDescription());
        }
    }

}
