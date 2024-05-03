package de.garrafao.phitag.computationalannotator.common.model.application.data;

import lombok.Getter;

@Getter
public class OpenAIToKenDto {
    private final int  inputToken;

    private final int outputToken;


    public OpenAIToKenDto(final int inputToken, final int outputToken) {
        this.inputToken = inputToken;
        this.outputToken = outputToken;
    }

    @Override
    public String toString() {
        return "OpenAIToKenDto{" +
                "inputToken=" + inputToken +
                ", outputToken=" + outputToken +
                '}';
    }
}
