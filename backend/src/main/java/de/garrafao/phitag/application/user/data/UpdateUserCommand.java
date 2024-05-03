package de.garrafao.phitag.application.user.data;

import java.util.Set;

import org.apache.commons.lang3.Validate;

import lombok.Getter;

@Getter
public class UpdateUserCommand {

    // If a field is empty, it will not be updated

    private final String newUsername;
    private final String newEmail;

    private final String newPassword;

    private final String newVisibility;
    private final String newUsecase;
    
    private final Set<String> newLanguages;
    private final String newDescription;

    private final String currentPassword;

    public UpdateUserCommand(final String newUsername, final String newEmail, final String newPassword, final String newVisibility, final String newUsecase, final Set<String> newLanguages, final String newDescription, final String currentPassword) {   
        
        Validate.notNull(currentPassword, "currentPassword must not be null");
        
        this.newUsername = newUsername;
        this.newEmail = newEmail;

        this.newPassword = newPassword;
        
        this.newVisibility = newVisibility;
        this.newUsecase = newUsecase;

        this.newLanguages = newLanguages;
        this.newDescription = newDescription;

        this.currentPassword = currentPassword;
    }

    @Override
    public String toString() {
        return "UpdateUserCommand [newUsername=" + newUsername + ", newEmail=" + newEmail + ", newPassword=" + newPassword + ", newLanguages=" + newLanguages + ", newDescription=" + newDescription + "]";
    }
    
}
