package de.garrafao.phitag.domain.tutorial.usepair.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairTutorialJudgementNotFoundException extends CustomRuntimeException {

    public UsePairTutorialJudgementNotFoundException() {
        super("Judgement not found");
    }

}
