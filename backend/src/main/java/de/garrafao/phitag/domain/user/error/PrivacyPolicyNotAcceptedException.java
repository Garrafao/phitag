package de.garrafao.phitag.domain.user.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class PrivacyPolicyNotAcceptedException extends CustomRuntimeException {

    public PrivacyPolicyNotAcceptedException() {
        super("Privacy policy not accepted");
    }
    
}
