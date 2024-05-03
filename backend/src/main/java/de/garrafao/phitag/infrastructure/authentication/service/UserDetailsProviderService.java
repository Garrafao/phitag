package de.garrafao.phitag.infrastructure.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.infrastructure.persistence.jpa.user.UserRepositoryBridge;

@Service
public class UserDetailsProviderService implements UserDetailsService {

    private final UserRepositoryBridge userRepositoryBridge;

    @Autowired
    public UserDetailsProviderService(UserRepositoryBridge userRepositoryBridge) {
        this.userRepositoryBridge = userRepositoryBridge;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepositoryBridge.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (!userDetails.isEnabled() || !userDetails.isAccountNonLocked() || !userDetails.isAccountNonExpired()) {
            throw new UsernameNotFoundException(username);
        }

        return userDetails;
    }

    
}
