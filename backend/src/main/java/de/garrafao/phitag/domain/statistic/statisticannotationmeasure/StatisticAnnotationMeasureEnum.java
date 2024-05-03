package de.garrafao.phitag.domain.statistic.statisticannotationmeasure;

public enum StatisticAnnotationMeasureEnum {
    PERCENTAGE_AGREEMENT("PERCENTAGE_AGREEMENT", "Percentage Agreement"),
    SCOTT_PI("SCOTT_PI", "Scott's Pi"),
    COHEN_KAPPA("COHEN_KAPPA", "Cohen's Kappa"),
    RANDOLPH_KAPPA("RANDOLPH_KAPPA", "Randolph's Kappa"),
    FLEISS_KAPPA("FLEISS_KAPPA", "Fleiss' Kappa"),
    HUBERT_KAPPA("HUBERT_KAPPA", "Hubert's Kappa"),
    KRIPPENDORFF_ALPHA_INTERVAL("KRIPPENDORFF_ALPHA_INTERVAL", "Krippendorff's Alpha - Interval"),
    KRIPPENDORFF_ALPHA_NOMINAL("KRIPPENDORFF_ALPHA_NOMINAL", "Krippendorff's Alpha - Nominal"),
    KRIPPENDORFF_ALPHA_ORDINAL("KRIPPENDORFF_ALPHA_ORDINAL", "Krippendorff's Alpha - Ordinal"),
    KRIPPENDORFF_ALPHA_RATIO("KRIPPENDORFF_ALPHA_RATIO", "Krippendorff's Alpha - Ratio");

    private final String id;
    private final String name;

    StatisticAnnotationMeasureEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public static StatisticAnnotationMeasureEnum fromId(String id) {
        for (StatisticAnnotationMeasureEnum statisticMeasureEnum : StatisticAnnotationMeasureEnum.values()) {
            if (statisticMeasureEnum.getId().equals(id)) {
                return statisticMeasureEnum;
            }
        }
        return null;
    }

    public static StatisticAnnotationMeasureEnum fromName(String name) {
        for (StatisticAnnotationMeasureEnum statisticMeasureEnum : StatisticAnnotationMeasureEnum.values()) {
            if (statisticMeasureEnum.getName().equals(name)) {
                return statisticMeasureEnum;
            }
        }
        return null;
    }
}
