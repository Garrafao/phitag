package de.garrafao.phitag.application.authentication.data;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.authentication.AuthenticationToken;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class AuthenticationTokenDto {

    private final String authenticationToken;

    private AuthenticationTokenDto(@NonNull final String authenticationToken) {
        Validate.notBlank(authenticationToken);
        this.authenticationToken = authenticationToken;
    }

    public static AuthenticationTokenDto from(@NonNull final AuthenticationToken authenticationToken) {
        return new AuthenticationTokenDto(authenticationToken.token());
    }

    @Override
    public String toString() {
        return "AuthenticationTokenDto{" + "token='" + authenticationToken + '\'' + '}';
    }

}
