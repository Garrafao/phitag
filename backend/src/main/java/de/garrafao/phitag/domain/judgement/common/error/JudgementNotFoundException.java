package de.garrafao.phitag.domain.judgement.common.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class JudgementNotFoundException extends CustomRuntimeException {

    public JudgementNotFoundException() {
        super("Judgement not found");
    }

    public JudgementNotFoundException(String message) {
        super(message);
    }
}
