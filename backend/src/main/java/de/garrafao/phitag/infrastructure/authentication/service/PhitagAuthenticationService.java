package de.garrafao.phitag.infrastructure.authentication.service;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import de.garrafao.phitag.domain.authentication.IAuthenticationService;
import de.garrafao.phitag.domain.user.User;

import org.springframework.security.authentication.AuthenticationManager;

@Component
public class PhitagAuthenticationService implements IAuthenticationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public PhitagAuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public User authenticate(String username, String password) {
        Validate.notBlank(username, "Email must not be blank");
        Validate.notBlank(password, "Password must not be blank");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);

        return (User) authentication.getPrincipal();

    }
    
}
