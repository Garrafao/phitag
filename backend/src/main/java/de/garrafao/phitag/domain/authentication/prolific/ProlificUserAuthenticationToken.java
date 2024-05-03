package de.garrafao.phitag.domain.authentication.prolific;

import lombok.NonNull;

public final record ProlificUserAuthenticationToken(@NonNull String token) {

    @Override
    public String toString() {
        return "AuthenticationToken{" + "token='" + token + '\'' + '}';
    }
}
