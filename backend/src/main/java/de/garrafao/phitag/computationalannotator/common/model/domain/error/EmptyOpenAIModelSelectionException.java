package de.garrafao.phitag.computationalannotator.common.model.domain.error;

import de.garrafao.phitag.domain.error.CustomRuntimeException;

public class EmptyOpenAIModelSelectionException extends CustomRuntimeException {

    public EmptyOpenAIModelSelectionException() {
        super("No model selected");
    }
    
}
