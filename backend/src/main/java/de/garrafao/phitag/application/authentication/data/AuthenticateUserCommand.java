package de.garrafao.phitag.application.authentication.data;

import lombok.Getter;

@Getter
public final class AuthenticateUserCommand {

    private String username;
    private String password;

    public AuthenticateUserCommand(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AuthenticateUserCommand{" + "username='" + username + '\'' + '}';
    }

}
