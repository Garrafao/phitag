package de.garrafao.phitag.application.dictionary.parser.wiktionaryxml.data;

/**
 * This enum contains all regex patterns for the part of speech for the English
 * language.
 * 
 * This is not a complete list of all possible part of speech values.
 *
 * <ul>
 * <li>Adjective</li>
 * <li>Adverb</li>
 * <li>Conjunction</li>
 * <li>Determiner</li>
 * <li>Interjection</li>
 * <li>Noun</li>
 * <li>Numeral</li>
 * <li>Particle</li>
 * <li>Preposition</li>
 * <li>Pronoun</li>
 * <li>Proper noun</li>
 * <li>Verb</li>
 * <li>Symbol</li>
 * </ul>
 * 
 * 
 */
public enum EnglishPartOfSpeechRegexPattern {
    ADJECTIVE("Adjective"),
    ADVERB("Adverb"),
    CONJUNCTION("Conjunction"),
    DETERMINER("Determiner"),
    INTERJECTION("Interjection"),
    NOUN("Noun"),
    NUMERAL("Numeral"),
    PARTICLE("Particle"),
    PREPOSITION("Preposition"),
    PRONOUN("Pronoun"),
    PROPER_NOUN("Proper noun"),
    VERB("Verb"),
    SYMBOL("Symbol");

    private final String regex;

    private EnglishPartOfSpeechRegexPattern(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static String getOrGroup() {
        String orGroup = "(";
        for (EnglishPartOfSpeechRegexPattern pattern : EnglishPartOfSpeechRegexPattern.values()) {
            orGroup += pattern.getRegex() + "|";
        }
        orGroup = orGroup.substring(0, orGroup.length() - 1);
        orGroup += ")";
        return orGroup;
    } 

    public static String getNonCapturingOrGroup() {
        String orGroup = "(:?";
        for (EnglishPartOfSpeechRegexPattern pattern : EnglishPartOfSpeechRegexPattern.values()) {
            orGroup += pattern.getRegex() + "|";
        }
        orGroup = orGroup.substring(0, orGroup.length() - 1);
        orGroup += ")";
        return orGroup;
    }

}
