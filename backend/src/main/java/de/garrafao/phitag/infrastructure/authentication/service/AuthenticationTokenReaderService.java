package de.garrafao.phitag.infrastructure.authentication.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.domain.authentication.AuthenticationToken;
import de.garrafao.phitag.domain.authentication.IAuthenticationTokenReaderService;
import de.garrafao.phitag.infrastructure.authentication.JwtAuthenticationKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class AuthenticationTokenReaderService implements IAuthenticationTokenReaderService {

    private static final String AUTHENTICATION_TOKEN_PREFIX = "Bearer";

    private final JwtAuthenticationKey jwtAuthenticationKey;

    private final UserDetailsProviderService userDetailsProviderService;

    @Autowired
    public AuthenticationTokenReaderService(JwtAuthenticationKey jwtAuthenticationKey,
            UserDetailsProviderService userDetailsProviderService) {
        this.jwtAuthenticationKey = jwtAuthenticationKey;
        this.userDetailsProviderService = userDetailsProviderService;
    }

    @Override
    public boolean isValid(final AuthenticationToken authenticationToken) {
        try {
            this.validateTokenAndGetClaims(authenticationToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public UserDetails getUserDetailsFromToken(final AuthenticationToken authenticationToken) {
        return userDetailsProviderService.loadUserByUsername(getUsername(authenticationToken));
    }

    public String getUsername(final AuthenticationToken authenticationToken) {
        return this.validateTokenAndGetClaims(authenticationToken).getSubject();
    }

    public List<String> getRoles(final AuthenticationToken authenticationToken) {
        return (List<String>) this.validateTokenAndGetClaims(authenticationToken).get("roles");
    }

    public Date getExpirationDate(final AuthenticationToken authenticationToken) {
        return this.validateTokenAndGetClaims(authenticationToken).getExpiration();
    }

    /**
     * This method is used to get the claims from the token.
     * BUT, this method is also a verifying method for a token.
     * Hence, use this method for all operations on the token, to ensure that the token is valid.  
    */ 
    private Claims validateTokenAndGetClaims(final AuthenticationToken authenticationToken) {
        Validate.notNull(authenticationToken);
        Validate.notNull(this.jwtAuthenticationKey.getKey());

        String token = authenticationToken.token().replace(AUTHENTICATION_TOKEN_PREFIX, "");

        // Due to parsing the token, the validity of the token is checked. See https://github.com/jwtk/jjwt#verification-key
        return Jwts.parser().setSigningKey(this.jwtAuthenticationKey.getKey()).parseClaimsJws(token).getBody();
    }

}
