package de.garrafao.phitag.domain.notification.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class NotificationException extends CustomRuntimeException {

    public NotificationException(final String message) {
        super(message);
    }


    
}
