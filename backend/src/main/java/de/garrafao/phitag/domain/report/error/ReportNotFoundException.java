package de.garrafao.phitag.domain.report.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class ReportNotFoundException extends CustomRuntimeException {

    private static final long serialVersionUID = 1L;

    public ReportNotFoundException() {
        super("Report not found");
    }

    public ReportNotFoundException(final String message) {
        super(message);
    }
    
}
