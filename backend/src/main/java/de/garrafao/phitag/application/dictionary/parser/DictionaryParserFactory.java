package de.garrafao.phitag.application.dictionary.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.dictionary.dictionary.data.DictionaryFileType;
import de.garrafao.phitag.application.dictionary.parser.customxml.CustomXMLParser;
import de.garrafao.phitag.application.dictionary.parser.wiktionaryxml.EnglishWiktionaryXMLParser;
import de.garrafao.phitag.application.dictionary.parser.wiktionaryxml.GermanWiktionaryXMLParser;

@Service
public class DictionaryParserFactory {

    private final CustomXMLParser customXMLParser;

    private final GermanWiktionaryXMLParser germanWiktionaryXMLParser;

    private final EnglishWiktionaryXMLParser englishWiktionaryXMLParser;

    // Wanted to make it static, but this is a workaround for my inability
    @Autowired
    public DictionaryParserFactory(
            final CustomXMLParser customXMLParser,
            final GermanWiktionaryXMLParser germanWiktionaryXMLParser,
            final EnglishWiktionaryXMLParser englishWiktionaryXMLParser) {
        this.customXMLParser = customXMLParser;
        this.germanWiktionaryXMLParser = germanWiktionaryXMLParser;
        this.englishWiktionaryXMLParser = englishWiktionaryXMLParser;
    }

    public IDictionaryParser getParser(final DictionaryFileType fileType) {
        switch (fileType) {
            case CUSTOM_XML:
                return customXMLParser;
            case WIKTIONARY_XML_DE:
                return germanWiktionaryXMLParser;
            case WIKTIONARY_XML_EN:
                return englishWiktionaryXMLParser;
            default:
                throw new IllegalArgumentException("Unknown file type: " + fileType);
        }
    }

}
