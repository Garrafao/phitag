package de.garrafao.phitag.application.dictionary.parser.wiktionaryxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.dictionary.dictionary.data.DictionaryFileType;
import de.garrafao.phitag.application.dictionary.parser.IDictionaryParser;
import de.garrafao.phitag.application.dictionary.parser.wiktionaryxml.data.EnglishPartOfSpeechRegexPattern;
import de.garrafao.phitag.application.dictionary.parser.wiktionaryxml.data.generated.*;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryRepository;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryRepository;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleRepository;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseRepository;
import de.garrafao.phitag.domain.helper.Pair;
import liquibase.pro.packaged.d;

@Service
public class EnglishWiktionaryXMLParser implements IDictionaryParser {

    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Dictionary repository dependencies

    private final DictionaryRepository dictionaryRepository;

    private final DictionaryEntryRepository dictionaryEntryRepository;

    private final DictionaryEntrySenseRepository dictionaryEntrySenseRepository;

    private final DictionaryEntrySenseExampleRepository dictionaryEntrySenseExampleRepository;

    // Other

    @Autowired
    public EnglishWiktionaryXMLParser(
            final CommonService commonService,
            final ValidationService validationService,

            final DictionaryRepository dictionaryRepository,
            final DictionaryEntryRepository dictionaryEntryRepository,
            final DictionaryEntrySenseRepository dictionaryEntrySenseRepository,
            final DictionaryEntrySenseExampleRepository dictionaryEntrySenseExampleRepository) {
        this.commonService = commonService;
        this.validationService = validationService;

        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryEntryRepository = dictionaryEntryRepository;
        this.dictionaryEntrySenseRepository = dictionaryEntrySenseRepository;
        this.dictionaryEntrySenseExampleRepository = dictionaryEntrySenseExampleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void parse(final Dictionary dictionary, final MultipartFile file) {
        MediaWikiType mediaWiki;
        try {
            mediaWiki = unmarshal(file);
        } catch (IOException | JAXBException e) {
            throw new IllegalArgumentException("Unable to parse file", e);
        }

        mediaWiki.getPage().forEach(page -> parsePage(dictionary, page));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictionaryFileType getFileType() {
        return DictionaryFileType.WIKTIONARY_XML_EN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStreamResource export(final Dictionary dictionary) {
        try {
            final MediaWikiType mediaWiki = convert(dictionary);

            final JAXBContext jaxbContext = JAXBContext.newInstance(MediaWikiType.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(mediaWiki, outputStream);

            return new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (final JAXBException | IOException e) {
            throw new IllegalArgumentException("Could not marshal dictionary");
        }
    }

    private MediaWikiType unmarshal(MultipartFile file) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MediaWikiType.class);
        return (MediaWikiType) jaxbContext.createUnmarshaller().unmarshal(file.getInputStream());
    }

    @Transactional
    private void parsePage(final Dictionary dictionary, final PageType page) {
        if (page.getRevisionOrUpload().isEmpty()) {
            return;
        }

        RevisionType revision = (RevisionType) page.getRevisionOrUpload().get(0);
        if (revision.getText() == null) {
            return;
        }

        List<DictionaryEntry> dictionaryEntries = new ArrayList<>();
        List<DictionaryEntrySense> dictionaryEntrySenses = new ArrayList<>();
        List<DictionaryEntrySenseExample> dictionaryEntrySenseExamples = new ArrayList<>();

        String text = revision.getText().getValue();
        List<Pair<String, List<String>>> posAndText = extractHeadwordPoSEntries(text);
        posAndText.forEach(
            entry -> {
                DictionaryEntry dictionaryEntry = new DictionaryEntry(dictionary, page.getTitle(), entry.getLeft());
                dictionaryEntries.add(dictionaryEntry);
                entry.getRight().forEach(
                    sense -> {
                        var parsed = parseSense(sense);
                        parsed.forEach(senseentry -> {
                            DictionaryEntrySense dictionaryEntrySense = new DictionaryEntrySense(dictionaryEntry, senseentry.getLeft(), dictionaryEntrySenses.size());
                            dictionaryEntrySenses.add(dictionaryEntrySense);

                            senseentry.getRight().forEach(
                                example -> {
                                    DictionaryEntrySenseExample dictionaryEntrySenseExample = new DictionaryEntrySenseExample(dictionaryEntrySense, example, 0);
                                    dictionaryEntrySenseExamples.add(dictionaryEntrySenseExample);
                                }
                            );

                        });
                    }
                );
            }
        );
        
        dictionaryEntryRepository.saveAll(dictionaryEntries);
        dictionaryEntrySenseRepository.saveAll(dictionaryEntrySenses);
        dictionaryEntrySenseExampleRepository.saveAll(dictionaryEntrySenseExamples);
    }

    /**
     * As the english wiktionary combines all pos of a word into one text element,
     * as well as language, we need to seperate them first.
     * Possible PoS are (https://en.wiktionary.org/wiki/Category:en:Parts_of_speech)
     * (this list is not complete)
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
     * The seperation is done by splitting the text element at each PoS and then
     * removing the PoS from the text.
     * 
     * Example:
     * ==English==
     * 
     * ===Noun===
     * {{en-noun}}
     * <some text>
     * 
     * ===Verb===
     * {{en-verb}}
     * <some text>
     * 
     * 
     * 
     * @param text The text element to seperate
     * @return A list of pairs of the form (PoS, senses as list of strings)
     */
    private List<Pair<String, List<String>>> extractHeadwordPoSEntries(final String text) {
        List<Pair<String, List<String>>> result = new ArrayList<>();

        String english = extractEnglish(text);
        if (english == null) {
            return result;
        }

        List<String> etymologies = extractEtymologies(english);
        if (etymologies.isEmpty()) {
            return result;
        }

        List<Pair<String, String>> posEntries = new ArrayList<>();

        for (String etymology : etymologies) {
            posEntries.addAll(extractPoSFromEtymology(etymology));
        }

        if (posEntries.isEmpty()) {
            posEntries.addAll(extractPoSFromNonEtymology(english));
        }

        if (posEntries.isEmpty()) {
            return result;
        }

        for (Pair<String, String> posEntry : posEntries) {
            List<String> senses = extractSenses(posEntry.getRight());
            if (senses.isEmpty()) {
                continue;
            }

            result.add(new Pair<>(posEntry.getLeft(), senses));
        }

        return result;
    }

    private String extractEnglish(final String text) {
        Pattern englishEntryPattern = Pattern.compile(
                "==English==\n" + //
                        "(?<English>.*?)" + //
                        "(?=\n==[A-z\s]+==\n" + //
                        "|$)",
                Pattern.DOTALL);
        Matcher matcher = englishEntryPattern.matcher(text);

        if (!matcher.find()) {
            return null;
        }

        return matcher.group("English");
    }

    private List<String> extractEtymologies(final String entry) {
        Pattern etymologyPattern = Pattern.compile(
                "===Etymology\s?[0-9]*===(?<Etymology>.*?)(?=\n===[A-z\s]+===\n|$)",
                Pattern.DOTALL);
        Matcher matcher = etymologyPattern.matcher(entry);

        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group("Etymology"));
        }

        return result;
    }

    private List<Pair<String, String>> extractPoSFromEtymology(final String etymology) {
        Pattern posPattern = Pattern.compile(
                "====(?<PoS>" + EnglishPartOfSpeechRegexPattern.getOrGroup()
                        + ")====(?<Sense>.*?)(?=\n====[A-z\s]+====\n|$)",
                Pattern.DOTALL);
        Matcher matcher = posPattern.matcher(etymology);

        List<Pair<String, String>> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(new Pair<>(matcher.group("PoS"), matcher.group("Sense")));
        }

        return result;
    }

    private List<Pair<String, String>> extractPoSFromNonEtymology(final String etymology) {
        Pattern posPattern = Pattern.compile(
                "===(?<PoS>" + EnglishPartOfSpeechRegexPattern.getOrGroup()
                        + ")===(?<Sense>.*?)(?=\n===[A-z\s]+===\n|$)",
                Pattern.DOTALL);
        Matcher matcher = posPattern.matcher(etymology);

        List<Pair<String, String>> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(new Pair<>(matcher.group("PoS"), matcher.group("Sense")));
        }

        return result;
    }

    private List<String> extractSenses(final String postext) {
        Pattern senses = Pattern.compile("(?<Senses>#\\s.*?)(?=\n#\s|\n\n|$)", Pattern.DOTALL);
        Matcher matcher = senses.matcher(postext);

        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group("Senses"));
        }

        return result;
    }

    /**
     * Extracts the sense(s) from the wiktionary text.
     * 
     * The text will be in the form of:
     * ```
     * # <sense 1>
     * #: <sense 1 example 1>
     * #: <sense 1 example 2>
     * #* <sense 1 quoted example 3>
     * ## <sense 1 sub-sense 1>
     * ## <sense 1 sub-sense 2>
     * ##: <sense 1 sub-sense 2 example 1>
     * ##*: <sense 1 sub-sense 2 quoted example 2>
     * # <sense 2>
     * ```
     * 
     * The seperation is done by splitting the text element at each sense and then
     * removing the sense from the text.
     * 
     * Following example illustrates the process:
     * ```
     * # An animal of the family [[Felidae]]:
     * #* {{quote-book|en|year=2011|author=Karl Kruszelnicki|title=Brain Food|isbn=1466828129|page=53|passage=Mammals need two genes to make the taste receptor for sugar. Studies in various '''cats''' (tigers, cheetahs and domestic cats) showed that one of these genes has mutated and no longer works.}}
     * #: {{syn|en|felid|feline|pantherine&lt;q:member of the subfamily [[Pantherinae]]&gt;|panther&lt;q:technically, all members of the genus ''[[Panthera]]''&gt;}}
     * ## A domesticated [[species]] (''[[Felis catus]]'') of [[feline]] animal, commonly kept as a house [[pet]]. {{defdate|from 8&lt;sup&gt;th&lt;/sup&gt;c.}}
     * ##* {{RQ:Besant Ivory Gate|II}}
     * ##*: At twilight in the summer there is never anybody to fear—man, woman, or '''cat'''—in the chambers and at that hour the mice come out. They do not eat parchment or foolscap or red tape, but they eat the luncheon crumbs.
     * ##: {{syn|en|puss|pussy|kitty|pussy-cat|kitty-cat|grimalkin|Thesaurus:cat}}
     * ##: {{hypernyms|en|housecat|malkin|kitten|mouser|tomcat}}
     * ## Any similar animal of the family [[Felidae]], which includes [[lion]]s, [[tiger]]s, [[bobcat]]s, [[leopard]]s, [[cougar]]s, [[cheetah]]s, [[caracal]]s, [[lynx]]es, and other such non-domesticated species.
     * ##* {{quote-book|en|year=1977|author=Peter Hathaway Capstick|title=Death in the Long Grass: A Big Game Hunter's Adventures in the African Bush|publisher=St. Martin's Press|page=44|passage=I grabbed it and ran over to the lion from behind, the '''cat''' still chewing thoughtfully on Silent's arm.}}
     * ##* '''1985''' January, George Laycock, &quot;Our American Lion&quot;, in Boy Scouts of America, ''{{w|Boys' Life}}'', 28.
     * ##*: If you should someday round a corner on the hiking trail and come face to face with a mountain lion, you would probably never forget the mighty '''cat'''.
     * ##* {{quote-book|en|year=2014|author=Dale Mayer|title=Rare Find. A Psychic Visions Novel|publisher=Valley Publishing|passage=She felt privileged to be here, living the experience inside the majestic '''cat''' [i.e. a tiger]; privileged to be part of their bond, even for only a few hours.}}
     * 
     * The output will be:
     * ```
     * List(
     *      Pair(
     *          "An animal of the family [[Felidae]]:",
     *          List(
     *              "{{quote-book|en|year=2011|author=Karl Kruszelnicki|title=Brain Food|isbn=1466828129|page=53|passage=Mammals need two genes to make the taste receptor for sugar. Studies in various '''cats''' (tigers, cheetahs and domestic cats) showed that one of these genes has mutated and no longer works.}}",
     *              "{{syn|en|felid|feline|pantherine&lt;q:member of the subfamily [[Pantherinae]]&gt;|panther&lt;q:technically, all members of the genus ''[[Panthera]]''&gt;}}"
     *          )
     *      ),
     *      Pair(
     *          "An animal of the family [[Felidae]]: ; A domesticated [[species]] (''[[Felis catus]]'') of [[feline]] animal, commonly kept as a house [[pet]]. {{defdate|from 8&lt;sup&gt;th&lt;/sup&gt;c.}}",
     *         List(
     *              "{{RQ:Besant Ivory Gate|II}}",
     *              "At twilight in the summer there is never anybody to fear—man, woman, or '''cat'''—in the chambers and at that hour the mice come out. They do not eat parchment or foolscap or red tape, but they eat the luncheon crumbs.",
     *              "{{syn|en|puss|pussy|kitty|pussy-cat|kitty-cat|grimalkin|Thesaurus:cat}}",
     * ...
     * )
     * 
     * @param sense
     * @return
     */
    private List<Pair<String, List<String>>> parseSense(final String sense) {
        final List<Pair<String, List<String>>> result = new ArrayList<>();

        final String[] senses = sense.split("\n");
        String mainsense = "";
        for (String string : senses) {
            String trimmed = string.trim();
            // Sense definitions
            if (trimmed.startsWith("# ")) {
                result.add(new Pair<>(trimmed.split(" ", 2)[1], new ArrayList<>()));
                mainsense = trimmed.split(" ", 2)[1];
                continue;
            }

            if (trimmed.startsWith("## ")) {
                result.add(new Pair<>(mainsense + "; " + trimmed.split(" ", 2)[1], new ArrayList<>()));
                continue;
            }

            // Examples
            if (trimmed.startsWith("#: ") || trimmed.startsWith("##: ") || trimmed.startsWith("#* ") || trimmed.startsWith("##* ")) {
                result.get(result.size() - 1).getRight().add(trimmed.split(" ", 2)[1]);
                continue;
            }

            // Contiued example
            if (trimmed.startsWith("#*: ") || trimmed.startsWith("##*: ")) {
                result.get(result.size() - 1).getRight().get(result.get(result.size() - 1).getRight().size() - 1)
                        .concat(" " + trimmed.split(" ", 2)[1]);
                continue;
            }
        }

        return result;
    }

    private MediaWikiType convert(final Dictionary dictionary) throws JAXBException, IOException {
        final MediaWikiType mediaWiki = new MediaWikiType();

        mediaWiki.setLang("en");
        mediaWiki.setVersion("0.10");


        // Entries
        final List<DictionaryEntry> dictionaryEntries = dictionaryEntryRepository
                .findAllByIdDictionaryidDnameAndIdDictionaryidUname(dictionary.getId().getDname(),
                        dictionary.getId().getUname());

        groupbyHeadword(dictionaryEntries).forEach((headword, entries) -> {
            final PageType page = new PageType();
            page.setTitle(headword);

            final RevisionType revision = new RevisionType();
            final ContributorType contributor = new ContributorType();
            final CommentType comment = new CommentType();
            contributor.setUsername(dictionary.getId().getUname());
            revision.setContributor(contributor);
            comment.setValue("Exported from PhiTag");
            revision.setComment(comment);

            final TextType text = new TextType();
            text.setValue(marshalEntry(entries));
            revision.setText(text);

            page.getRevisionOrUpload().add(revision);
            mediaWiki.getPage().add(page);
        });

        return mediaWiki;

    }

    private Map<String, List<DictionaryEntry>> groupbyHeadword(final List<DictionaryEntry> entries) {
        return entries.stream()
                .collect(Collectors.groupingBy(DictionaryEntry::getHeadword));
    }

    private String marshalEntry(final List<DictionaryEntry> entries) {
        final StringBuilder sb = new StringBuilder();

        sb.append("==English==\n");
        entries.forEach(entry -> {
            sb.append("===" + entry.getPartofspeech() + "===\n");
            sb.append("{{en-" + entry.getPartofspeech().toLowerCase() + "}}\n");
            sb.append("\n");

            entry.getSenses().forEach(sense -> {
                sb.append("# " + sense.getDefinition() + "\n");
                sense.getExamples().forEach(example -> {
                    sb.append("#: " + example.getExample() + "\n");
                });
            });

            sb.append("\n");
        });

        return sb.toString();
    }
}
