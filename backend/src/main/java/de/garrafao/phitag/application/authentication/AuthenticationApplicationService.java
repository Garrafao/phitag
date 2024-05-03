package de.garrafao.phitag.application.authentication;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import de.garrafao.phitag.application.authentication.data.AuthenticationTokenDto;
import de.garrafao.phitag.application.authentication.data.ValidateAuthenticationTokenCommand;
import de.garrafao.phitag.domain.authentication.AuthenticationToken;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.infrastructure.authentication.service.AuthenticationTokenGenerationService;
import de.garrafao.phitag.infrastructure.authentication.service.AuthenticationTokenReaderService;
import de.garrafao.phitag.infrastructure.authentication.service.PhitagAuthenticationService;

@Service
public class AuthenticationApplicationService {
    
    private final AuthenticationTokenGenerationService authenticationTokenGenerationService;
    private final AuthenticationTokenReaderService authenticationTokenReaderService;
    private final PhitagAuthenticationService authenticationService;

    @Autowired
    public AuthenticationApplicationService(AuthenticationTokenGenerationService authenticationTokenGenerationService,
            AuthenticationTokenReaderService authenticationTokenReaderService,
            PhitagAuthenticationService authenticationService) {
        this.authenticationTokenGenerationService = authenticationTokenGenerationService;
        this.authenticationTokenReaderService = authenticationTokenReaderService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public AuthenticationTokenDto authenticateUser(final AuthenticateUserCommand authenticateUserCommand) {
        Validate.notNull(authenticateUserCommand, "authenticateUserCommand must not be null");

        User user;

        try{
            user = authenticationService.authenticate(authenticateUserCommand.getUsername(), authenticateUserCommand.getPassword());
        } catch (Exception ex) {
            throw new IllegalArgumentException(String.format("User %s not authenticated", authenticateUserCommand.getUsername()));
        }

        AuthenticationToken authenticationToken = authenticationTokenGenerationService.generateToken(user);
        return AuthenticationTokenDto.from(authenticationToken);
    }

    public boolean validateAuthenticationToken(final ValidateAuthenticationTokenCommand validateAuthenticationTokenCommand) {
        Validate.notNull(validateAuthenticationTokenCommand, "validateAuthenticationTokenCommand must not be null");
        return authenticationTokenReaderService.isValid(new AuthenticationToken(validateAuthenticationTokenCommand.getAuthenticationToken()));
    }

    public String getUsernameFromAuthenticationToken(final String authenticationToken) {
        Validate.notNull(authenticationToken, "authenticationTokenDto must not be null");
        return authenticationTokenReaderService.getUsername(new AuthenticationToken(authenticationToken));
    }
}
