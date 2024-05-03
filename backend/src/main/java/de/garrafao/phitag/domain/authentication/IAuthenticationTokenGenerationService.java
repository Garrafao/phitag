package de.garrafao.phitag.domain.authentication;

import de.garrafao.phitag.domain.user.User;

public interface IAuthenticationTokenGenerationService {

    AuthenticationToken generateToken(final User user);
    
}
