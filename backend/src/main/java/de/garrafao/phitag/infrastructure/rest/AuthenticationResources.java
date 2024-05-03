package de.garrafao.phitag.infrastructure.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.authentication.AuthenticationApplicationService;
import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import de.garrafao.phitag.application.authentication.data.AuthenticationTokenDto;
import de.garrafao.phitag.application.authentication.data.ValidateAuthenticationTokenCommand;

@RestController
@RequestMapping(value = "/api/v1/authentication")
public class AuthenticationResources {

    private final AuthenticationApplicationService  authenticationApplicationService;

    @Autowired
    public AuthenticationResources(AuthenticationApplicationService authenticationApplicationService) {
        this.authenticationApplicationService = authenticationApplicationService;
    }

    @PostMapping(value = "/login")
    public AuthenticationTokenDto login(@RequestBody final AuthenticateUserCommand command) {
        return authenticationApplicationService.authenticateUser(command);
    }

    // Why? Why give api access to the user for validate? may cause a lot of problems
    @PostMapping(value = "/validate")
    public ResponseEntity<Void> validate(@RequestBody final ValidateAuthenticationTokenCommand command) {
        boolean isValid = authenticationApplicationService.validateAuthenticationToken(command);

        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
}
