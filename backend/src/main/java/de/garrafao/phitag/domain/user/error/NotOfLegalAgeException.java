package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class NotOfLegalAgeException extends CustomRuntimeException {

    public NotOfLegalAgeException() {
        super("User must be of legal age");
    }
    
}
