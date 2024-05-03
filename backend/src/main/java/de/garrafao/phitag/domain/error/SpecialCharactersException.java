package de.garrafao.phitag.domain.error;

public class SpecialCharactersException extends CustomRuntimeException {

    public SpecialCharactersException() {
        super("Special chars not allowed");
    }
    
}
