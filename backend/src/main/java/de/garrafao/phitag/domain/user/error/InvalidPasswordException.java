package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class InvalidPasswordException extends CustomRuntimeException {
    
    public InvalidPasswordException() {
        super("Password must contain at least one digit, one lowercase, one uppercase, one special character and must be at least 8 characters long");
    }
}
