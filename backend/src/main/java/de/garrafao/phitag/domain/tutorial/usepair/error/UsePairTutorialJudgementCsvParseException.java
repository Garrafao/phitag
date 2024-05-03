package de.garrafao.phitag.domain.tutorial.usepair.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class UsePairTutorialJudgementCsvParseException extends CustomRuntimeException {
    
    public UsePairTutorialJudgementCsvParseException() {
        super("CSV File for Use Pair Judgements is not correctly formatted");
    }
}
