package de.garrafao.phitag.application.validation;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.entitlement.data.EntitlementEnum;
import de.garrafao.phitag.application.language.data.LanguageEnum;
import de.garrafao.phitag.application.role.data.RoleEnum;
import de.garrafao.phitag.application.visibility.data.VisibilityEnum;
import de.garrafao.phitag.domain.annotationtype.error.AnnotationTypeNotFoundException;
import de.garrafao.phitag.domain.annotationtype.error.EmptyAnnotationTypeSelectionException;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.error.InvalidNameExcepion;
import de.garrafao.phitag.domain.language.error.EmptyLanguageSelectionException;
import de.garrafao.phitag.domain.language.error.LanguageNotFoundException;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phase.data.PhaseStatusEnum;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.user.error.InvalidEmailException;
import de.garrafao.phitag.domain.user.error.InvalidPasswordException;
import de.garrafao.phitag.domain.visibility.error.EmptyVisibilitySelectionException;
import de.garrafao.phitag.domain.visibility.error.VisibilityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.regex.Pattern;

/*
 * A service that provides validation functionality used or commonly implemented by other services.
 * This service is written in such a way, to allow chaining of validation methods.
 */
@Service
public class ValidationService {

    private final Pattern PASSWORD_PATTERN = Pattern
            .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");

    private final Pattern EMAILPATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    // Pattern for Username, Projectname, Phasename. Must contain only letters,
    // numbers and dashes.
    private final Pattern NAMEPATTERN = Pattern.compile("^[a-zA-Z0-9-]+$");

    // Service dependencies
    private final CommonService commonService;

    @Autowired
    public ValidationService(final CommonService commonService) {
        this.commonService = commonService;
    }

    // Common validation methods

    /**
     * Validates a password.
     * 
     * @param password The password to validate.
     * @return The password.
     * @throws InvalidPasswordException If the password is invalid.
     */
    public ValidationService password(final String password) {
        if (password == null || password.isEmpty() || password.isBlank()
                || !this.PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordException();
        }
        return this;
    }

    /**
     * Validates the given email address. Throws an exception if the email address
     * is invalid. The email address is valid if it matches the pattern
     * {@link #EMAILPATTERN}.
     * 
     * @param email The email address to validate.
     * @return {@ValidationService} for chaining
     * @throws InvalidEmailException if the email address is invalid.
     */
    public ValidationService email(final String email) {
        if (email == null || email.isEmpty() || email.isBlank() || !this.EMAILPATTERN.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
        return this;
    }

    /**
     * Validates the given name. The name must not be null, empty or blank and must
     * match the {@link #NAMEPATTERN}.
     * 
     * @return {@ValidationService} for chaining
     * @throws InvalidNameExcepion if the name is invalid
     */
    public ValidationService name(final String name) {
        if (name == null || name.isEmpty() || name.isBlank() || !this.NAMEPATTERN.matcher(name).matches()) {
            throw new InvalidNameExcepion("The name must consist of letters, numbers and dashes only");
        }
        return this;
    }

    /**
     * Validates that the user is an admin.
     * 
     * @param requester
     */
    public void validateUserIsAdmin(final User requester) {
        if (requester.getRoles().stream().noneMatch(r -> r.getName().equals(RoleEnum.ROLE_ADMIN.name()))) {
            throw new AccessDenidedException();
        }
    }

    // Validation of access rights to project (hence phases and so on)
    /**
     * Validates if the user has access to the project.
     * 
     * A user has access if:
     * - the user is the owner of the project
     * - the project is active and public
     * - the project is active and the user is a member of the project (i.e.
     * annotater)
     * 
     * @return {@ValidationService} for chaining
     * @throws AccessDenidedException if the user has no access to the project
     */
    public ValidationService projectAccess(final User requester, final Project project) {
        if (project.getOwner().equals(requester) || requester.isIsbot()) {
            return this;
        }

        if (project.isActive() && project.getVisibility().getName().equals(VisibilityEnum.VISIBILITY_PUBLIC.name())) {
            return this;
        }

        if (project.isActive()
                && this.commonService.isUserAnnotator(project.getId().getOwnername(), project.getId().getName(),
                        requester.getUsername())) {
            return this;
        }

        throw new AccessDenidedException();
    }

    // Validation of access rights to phases is in projectAccess handled

    /**
     * Higher level validation of access rights, where user should be owner or admin
     * of the project.
     * 
     * This handles data access, adding, exporting, etc. as well as the access to
     * the
     * Phase creation and Annotator adding.
     * 
     * @return {@ValidationService} for chaining
     * @throws AccessDenidedException if the user has no access to the project as
     *                                user is neither owner nor admin
     */
    public ValidationService projectAdminAccess(final User requester, final Project project) {
        if (project.getOwner().equals(requester) || requester.isIsbot()) {
            return this;
        }

        if (this.commonService
                .getAnnotator(project.getId().getOwnername(), project.getId().getName(), requester.getUsername())
                .getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_ADMIN.name())) {
            return this;
        }

        throw new AccessDenidedException();
    }

    /**
     * Higher level validation of access rights, where user should be owner of the
     * project.
     * 
     * @param requester
     * @param project
     * @return
     */
    public ValidationService projectOwnerAccess(final User requester, final Project project) {
        if (project.getOwner().equals(requester)) {
            return this;
        }

        throw new AccessDenidedException();
    }

    /**
     * Lower level validation of access rights to project and phase, where the user
     * is checked if it is an annotator of the project and phase.
     * 
     * This handles access to annotation
     * 
     * @return {@ValidationService} for chaining
     * @throws AccessDenidedException if the user has no access to the project as
     *                                user is not an annotator
     */
    public ValidationService projectAnnotatorAccess(final User requester, final Project project) {
        if (this.commonService.isUserAnnotator(project.getId().getOwnername(), project.getId().getName(),
                requester.getUsername())) {
            return this;
        }

        throw new AccessDenidedException();
    }

    /**
     * Validate if annotator has completed all required tutorial phases.
     * 
     * @return
     */
    public ValidationService phaseAnnotationAccess(final User requester, final Phase phase) {
        if (phase.getStatus().equals(PhaseStatusEnum.CLOSED.name())) {
            throw new AccessDenidedException("Phase is closed");
        }

        if (!this.commonService.isUserAnnotator(phase.getId().getProjectid().getOwnername(),
                phase.getId().getProjectid().getName(),
                requester.getUsername())) {
            throw new AccessDenidedException();
        }

        if (!phase.getTutorialRequirements().isEmpty()
                && !this.commonService.hasAnnotatorCompletedTutorialsOfPhase(requester.getUsername(),
                        phase.getId().getProjectid().getOwnername(), phase.getId().getProjectid().getName(),
                        phase.getId().getName())) {
            throw new AccessDenidedException();
        }

        final long numberOfInstances = this.commonService.countInstancesOfPhase(phase, false);

        if (numberOfInstances == 0) {
            throw new AccessDenidedException("No instances available for annotation.");
        }

        if (phase.isTutorial()) {
            final long countGoldJudgements = commonService.countJudgementsOfPhase(phase);

            if (countGoldJudgements == 0) {
                throw new AccessDenidedException("No Gold-Judgements available for tutorial.");
            }

            if (countGoldJudgements != numberOfInstances) {
                throw new AccessDenidedException("Gold judgements and instances do not match.");
            }

        }

        return this;
    }

    /**
     * Validate if requester is a bot.
     * 
     * @param requester The user to validate
     * @return {@ValidationService} for chaining
     */
    public ValidationService botAccess(final User requester) {
        if (requester.isIsbot()) {
            return this;
        }

        throw new AccessDenidedException();
    }

    /**
     * Validate if requester is a bot or an project admin.
     * 
     * @return {@ValidationService} for chaining
     * @throws AccessDenidedException if the user has no access to the project as
     *                                user is neither owner nor admin
     */
    public ValidationService projectAdminOrBot(final User requester, final Project project) {
        if (requester.isIsbot()) {
            return this;
        }

        return this.projectAdminAccess(requester, project);

    }

    // Validation of static values (i.e. language, visibility, annotation type)

    public ValidationService language(final String language) {
        // Instead of quering the database for each language, check if enum contains the
        // language
        if (!LanguageEnum.contains(language)) {
            throw new LanguageNotFoundException();
        }
        return this;
    }

    public ValidationService languageSet(final Set<String> languages) {
        if (languages == null || languages.isEmpty()) {
            throw new EmptyLanguageSelectionException();
        }

        languages.forEach(this::language);
        return this;
    }

    public ValidationService visibility(final String visibility) {
        if (!VisibilityEnum.contains(visibility)) {
            throw new VisibilityNotFoundException();
        }
        return this;
    }

    public ValidationService visibilitySet(final Set<String> visibilities) {
        if (visibilities == null || visibilities.isEmpty()) {
            throw new EmptyVisibilitySelectionException();
        }

        visibilities.forEach(this::visibility);
        return this;
    }

    public ValidationService annotationType(final String annotationType) {
        if (!VisibilityEnum.contains(annotationType)) {
            throw new AnnotationTypeNotFoundException();
        }
        return this;
    }

    public ValidationService annotationTypeSet(final Set<String> annotationTypes) {
        if (annotationTypes == null || annotationTypes.isEmpty()) {
            throw new EmptyAnnotationTypeSelectionException();
        }

        annotationTypes.forEach(this::annotationType);
        return this;
    }

    public ValidationService validateSenseExists(String dname, String uname, String entryId, String senseId) {
        this.commonService.getSenseEntry(senseId, entryId, dname, uname);
        return this;
    }

}
