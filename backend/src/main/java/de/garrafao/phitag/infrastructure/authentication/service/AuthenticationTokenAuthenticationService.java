package de.garrafao.phitag.infrastructure.authentication.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.domain.authentication.AuthenticationToken;

import static java.util.List.of;
import static java.util.Optional.ofNullable;

@Service
public class AuthenticationTokenAuthenticationService {

    private static final String AUTHENTICATION_TOKEN_HEADER = "Authorization";

    private final AuthenticationTokenReaderService authenticationTokenReaderService;

    @Autowired
    public AuthenticationTokenAuthenticationService(
            final AuthenticationTokenReaderService authenticationTokenReaderService) {
        this.authenticationTokenReaderService = authenticationTokenReaderService;
    }

    public Authentication getAuthenticationFromRequest(final HttpServletRequest request) {
        final String token = request.getHeader(AUTHENTICATION_TOKEN_HEADER);
        if (token == null || token.isEmpty()) {
            return null;
        }

        UserDetails userDetails;
        try {
            userDetails = authenticationTokenReaderService.getUserDetailsFromToken(new AuthenticationToken(token));
        } catch (Exception e) {
            return null;
        }

        if (userDetails == null || userDetails.getUsername() == null || userDetails.getUsername().isEmpty()) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of()));

    }
}
