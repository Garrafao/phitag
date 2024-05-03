package de.garrafao.phitag.domain.tutorial.userankrelative.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankRelativeJudgementNotFoundException extends CustomRuntimeException {

    public UseRankRelativeJudgementNotFoundException() {
        super("Judgement not found");
    }

}
