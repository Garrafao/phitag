package de.garrafao.phitag.domain.authentication;

import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthenticationTokenReaderService {

    boolean isValid(AuthenticationToken authenticationToken);

    UserDetails getUserDetailsFromToken(final AuthenticationToken authenticationToken);
    
}
