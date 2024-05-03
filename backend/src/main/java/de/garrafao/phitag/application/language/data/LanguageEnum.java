package de.garrafao.phitag.application.language.data;

public enum LanguageEnum {
    LANGUAGE_GERMAN("German"),
    LANGUAGE_ENGLISH("English"),
    LANGUAGE_FRENCH("French"),
    LANGUAGE_SPANISH("Spanish"),
    LANGUAGE_ITALIAN("Italian"),
    LANGUAGE_PORTUGUESE("Portuguese"),
    LANGUAGE_RUSSIAN("Russian"),
    // LANGUAGE_CHINESE("Chinese"),
    // LANGUAGE_JAPANESE("Japanese"),
    // LANGUAGE_KOREAN("Korean"),
    // LANGUAGE_ARABIC("Arabic"),
    // LANGUAGE_HEBREW("Hebrew"),
    // LANGUAGE_HINDI("Hindi"),
    // LANGUAGE_TURKISH("Turkish"),
    LANGUAGE_DUTCH("Dutch"),
    LANGUAGE_POLISH("Polish"),
    // LANGUAGE_ROMANIAN("Romanian"),
    LANGUAGE_CZECH("Czech"),
    LANGUAGE_SWEDISH("Swedish");

    private final String name;

    LanguageEnum(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean contains(String name) {
        for (LanguageEnum language : LanguageEnum.values()) {
            if (language.name.equals(name)) {
                return true;
            }
        }
        return false;
    }
    
}
