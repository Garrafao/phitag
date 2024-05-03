package de.garrafao.phitag.application.sampling.data;

public enum SamplingEnum {
    SAMPLING_RANDOM_WITH_REPLACEMENT("SAMPLING_RANDOM_WITH_REPLACEMENT"),
    SAMPLING_RANDOM_WITHOUT_REPLACEMENT("SAMPLING_RANDOM_WITHOUT_REPLACEMENT"),
    SAMPLING_ID_ORDER("SAMPLING_ID_ORDER"),

    N_SAMPLING_RANDOM_WITHOUT_REPLACEMENT("N_SAMPLING_RANDOM_WITHOUT_REPLACEMENT"),
    N_SAMPLING_RANDOM_WITH_REPLACEMENT("N_SAMPLING_RANDOM_WITH_REPLACEMENT");


    private final String name;

    SamplingEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
