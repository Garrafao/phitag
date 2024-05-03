package de.garrafao.phitag.domain.statistic.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class StatisticException extends CustomRuntimeException {

    public StatisticException(String message) {
        super(message);
    }
    
}
