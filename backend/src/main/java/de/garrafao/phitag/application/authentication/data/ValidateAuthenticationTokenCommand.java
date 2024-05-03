package de.garrafao.phitag.application.authentication.data;

import org.apache.commons.lang3.Validate;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ValidateAuthenticationTokenCommand {

    private String authenticationToken;

    public ValidateAuthenticationTokenCommand() {}

    public ValidateAuthenticationTokenCommand(@NonNull final String authenticationToken) {
        Validate.notBlank(authenticationToken, "authenticationToken cannot be null or empty");
        this.authenticationToken = authenticationToken;
    }

    @Override
    public String toString() {
        return "ValidateTokenCommand{" + "token='" + authenticationToken + '\'' + '}';
    }
}
