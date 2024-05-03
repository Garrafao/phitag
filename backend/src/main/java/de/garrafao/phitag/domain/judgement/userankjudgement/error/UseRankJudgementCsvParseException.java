package de.garrafao.phitag.domain.judgement.userankjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UseRankJudgementCsvParseException extends CustomRuntimeException {
    
    public UseRankJudgementCsvParseException() {
        super("CSV File for Use Rank Judgements is not correctly formatted");
    }
}
