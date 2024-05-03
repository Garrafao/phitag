package de.garrafao.phitag.domain.error;

public class CsvParseException extends CustomRuntimeException {

    public CsvParseException() {
        super("CSV parse error");
    }

    public CsvParseException(String message) {
        super("CSV parse error: " + message);
    }
    
}
