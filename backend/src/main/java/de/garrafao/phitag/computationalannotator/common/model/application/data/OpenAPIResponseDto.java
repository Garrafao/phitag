package de.garrafao.phitag.computationalannotator.common.model.application.data;

import lombok.Getter;

@Getter
public class OpenAPIResponseDto {
    private final String judgement;


    public OpenAPIResponseDto(String judgement) {
        this.judgement = judgement;
    }

    @Override
    public String toString() {
        return "OpenAPIResponseDto{" +
                "judgement='" + judgement + '\'' +
                '}';
    }
}
