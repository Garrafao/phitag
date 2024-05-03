package de.garrafao.phitag.domain.judgement.userankpairjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankPairJudgementNotFoundException extends CustomRuntimeException {

    public UseRankPairJudgementNotFoundException() {
        super("Judgement not found");
    }

}
