package de.garrafao.phitag.infrastructure.persistence.jpa.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import de.garrafao.phitag.domain.user.User;

public interface UserRepositoryJpa extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    
    Optional<User> findByUsername(final String username);
    
    Optional<User> findByEmail(final String email);
    
    Optional<User> findByUsernameOrEmail(final String username, final String email);

}
