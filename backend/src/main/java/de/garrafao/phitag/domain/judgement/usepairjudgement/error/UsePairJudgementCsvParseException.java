package de.garrafao.phitag.domain.judgement.usepairjudgement.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairJudgementCsvParseException extends CustomRuntimeException {
    
    public UsePairJudgementCsvParseException() {
        super("CSV File for Use Pair Judgements is not correctly formatted");
    }
}
