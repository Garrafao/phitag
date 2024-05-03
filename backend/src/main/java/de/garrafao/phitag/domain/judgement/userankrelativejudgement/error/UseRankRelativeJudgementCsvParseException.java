package de.garrafao.phitag.domain.judgement.userankrelativejudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankRelativeJudgementCsvParseException extends CustomRuntimeException {
    
    public UseRankRelativeJudgementCsvParseException() {
        super("CSV File for Use Rank Judgements is not correctly formatted");
    }
}
