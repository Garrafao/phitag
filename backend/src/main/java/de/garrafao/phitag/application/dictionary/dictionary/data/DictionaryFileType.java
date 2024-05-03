package de.garrafao.phitag.application.dictionary.dictionary.data;

public enum DictionaryFileType {
    CUSTOM_XML("Custom-XML"),
    WIKTIONARY_XML_DE("Wiktionary-XML-DE"),
    WIKTIONARY_XML_EN("Wiktionary-XML-EN");

    private final String name;

    DictionaryFileType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static DictionaryFileType fromName(final String name) {
        for (final DictionaryFileType type : DictionaryFileType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static boolean isValid(final String name) {
        return DictionaryFileType.fromName(name) != null;
    }
}
