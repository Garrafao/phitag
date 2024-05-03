package de.garrafao.phitag.infrastructure.authentication.service;

import java.sql.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.domain.authentication.AuthenticationToken;
import de.garrafao.phitag.domain.authentication.IAuthenticationTokenGenerationService;
import de.garrafao.phitag.domain.role.Role;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.infrastructure.authentication.JwtAuthenticationKey;
import io.jsonwebtoken.Jwts;

@Service
public class AuthenticationTokenGenerationService implements IAuthenticationTokenGenerationService {

    private static final long EXPIRATION_TIME = 86400000; // 1 day

    private final JwtAuthenticationKey jwtAuthenticationKey;

    @Autowired
    public AuthenticationTokenGenerationService(final JwtAuthenticationKey jwtAuthenticationKey) {
        this.jwtAuthenticationKey = jwtAuthenticationKey;
    }

    @Override
    public AuthenticationToken generateToken(User user) {
        Validate.notNull(user, "User must not be null");
        Validate.notNull(jwtAuthenticationKey, "JwtAuthenticationKey must not be null");

        List<String> roles = user.getRoles().stream().map(Role::getName).toList();
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", roles)
                .claim("usecase", user.getUsecase().getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(jwtAuthenticationKey.getKey()).compact();

        return new AuthenticationToken(token);
    }


}
