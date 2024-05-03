package de.garrafao.phitag.domain.judgement.userankrelativejudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankRelativeJudgementNotFoundException extends CustomRuntimeException {

    public UseRankRelativeJudgementNotFoundException() {
        super("Judgement not found");
    }

}
