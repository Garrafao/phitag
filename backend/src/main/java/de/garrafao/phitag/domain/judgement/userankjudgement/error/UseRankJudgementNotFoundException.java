package de.garrafao.phitag.domain.judgement.userankjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankJudgementNotFoundException extends CustomRuntimeException {

    public UseRankJudgementNotFoundException() {
        super("Judgement not found");
    }

}
