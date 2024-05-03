package de.garrafao.phitag.domain.judgement.userankpairjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankPairJudgementCsvParseException extends CustomRuntimeException {
    
    public UseRankPairJudgementCsvParseException() {
        super("CSV File for Use Rank Judgements is not correctly formatted");
    }
}
