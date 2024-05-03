package de.garrafao.phitag.domain.judgement.usepairjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairJudgementNotFoundException extends CustomRuntimeException {

    public UsePairJudgementNotFoundException() {
        super("Judgement not found");
    }

}
