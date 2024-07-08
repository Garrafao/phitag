package de.garrafao.phitag.application.annotationtype.data;

public enum AnnotationTypeEnum {
    ANNOTATIONTYPE_USEPAIR("ANNOTATIONTYPE_USEPAIR"),
    ANNOTATIONTYPE_USERANK("ANNOTATIONTYPE_USERANK"),
    ANNOTATIONTYPE_USERANK_RELATIVE("ANNOTATIONTYPE_USERANK_RELATIVE"),

    ANNOTATIONTYPE_USERANK_PAIR("ANNOTATIONTYPE_USERANK_PAIR"),
    ANNOTATIONTYPE_WSSIM("ANNOTATIONTYPE_WSSIM"),
    ANNOTATIONTYPE_LEXSUB("ANNOTATIONTYPE_LEXSUB"),
    ANNOTATIONTYPE_CHOICE("ANNOTATIONTYPE_PREFERENCE"),

    ANNOTATIONTYPE_SPAN("ANNOTATIONTYPE_SPAN"),


    ANNOTATIONTYPE_SENTIMENT("ANNOTATIONTYPE_SENTIMENT");
    // ANNOTATIONTYPE_USESINGLE("ANNOTATIONTYPE_SINGLEUSE"),
    // ANNOTATIONTYPE_WORDREPLACEMENT("ANNOTATIONTYPE_WORDREPLACEMENT");

    private final String name;

    AnnotationTypeEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String name) {
        for (AnnotationTypeEnum annotationtype : AnnotationTypeEnum.values()) {
            if (annotationtype.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
