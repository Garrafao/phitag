package de.garrafao.phitag.infrastructure.authentication;

import java.security.Key;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationKey {

    private Key key;
    private final String signingKey;

    @Autowired
    public JwtAuthenticationKey(@Value("${phitag.security.jwt.signing-key}") final String signingKey) {
        Validate.notNull(signingKey, "signingKey must not be null");
        Validate.notBlank(signingKey, "signingKey must not be empty");
        this.signingKey = signingKey;
    }

    @PostConstruct
    public void initialize() {
        key = Keys.hmacShaKeyFor(signingKey.getBytes());
    }

    public Key getKey() {
        if (key == null) {
            throw new IllegalStateException("JWT key has not been initialized");
        }
        return key;
    }

}
