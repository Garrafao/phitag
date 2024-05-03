package de.garrafao.phitag.domain.authentication;

import de.garrafao.phitag.domain.user.User;

public interface IAuthenticationService {

    User authenticate(final String username, final String password);
    
}
