package de.garrafao.phitag.computationalannotator.common.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class WrongModelException extends CustomRuntimeException {
    public WrongModelException(String model) {
        super("The model"+" "+ model+" "+"does not exist");
    }
}
