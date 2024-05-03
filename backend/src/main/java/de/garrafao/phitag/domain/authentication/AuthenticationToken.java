package de.garrafao.phitag.domain.authentication;

import lombok.NonNull;

public final record AuthenticationToken(@NonNull String token) {

    @Override
    public String toString() {
        return "AuthenticationToken{" + "token='" + token + '\'' + '}';
    }
}
