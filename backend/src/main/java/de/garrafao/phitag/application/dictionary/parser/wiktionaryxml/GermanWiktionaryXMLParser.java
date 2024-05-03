package de.garrafao.phitag.application.dictionary.parser.wiktionaryxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

@Service
public class GermanWiktionaryXMLParser implements IDictionaryParser {

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
    public GermanWiktionaryXMLParser(
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

    private MediaWikiType unmarshal(MultipartFile file) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MediaWikiType.class);
        return (MediaWikiType) jaxbContext.createUnmarshaller().unmarshal(file.getInputStream());
    }

    @Override
    public DictionaryFileType getFileType() {
        return DictionaryFileType.WIKTIONARY_XML_DE;
    }

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

    @Transactional
    private void parsePage(final Dictionary dictionary, final PageType page) {
        if (page.getRevisionOrUpload().isEmpty()) {
            return;
        }

        RevisionType revision = (RevisionType) page.getRevisionOrUpload().get(0);
        if (revision.getText() == null) {
            return;
        }

        String text = revision.getText().getValue();
        String partOfSpeech = getPartOfSpeech(text);
        List<Pair<Integer, String>> senses = getSenses(text);
        HashMap<Integer, ArrayList<String>> examples = getExamples(text);

        DictionaryEntry entry = new DictionaryEntry(dictionary, page.getTitle(), partOfSpeech);
        entry = dictionaryEntryRepository.save(entry);

        for (Pair<Integer, String> sense : senses) {
            DictionaryEntrySense entrySense = new DictionaryEntrySense(entry, sense.getRight(), sense.getLeft());
            entrySense = dictionaryEntrySenseRepository.save(entrySense);

            ArrayList<String> exampleList = examples.getOrDefault(sense.getLeft(), new ArrayList<>());
            if (exampleList.isEmpty()) {
                continue;
            }

            int i = 0;
            for (String example : exampleList) {
                dictionaryEntrySenseExampleRepository.save(new DictionaryEntrySenseExample(entrySense, example, i++));
            }
        }
    }

    private String getPartOfSpeech(final String text) {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.startsWith("=== {{Wortart|")) {
                return line.substring(14).split("\\|")[0];
            }
        }
        return null;
    }

    private List<Pair<Integer, String>> getSenses(final String text) {
        String[] lines = text.split("\n\n");
        List<Pair<Integer, String>> senses = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("{{Bedeutungen}}")) {
                for (String sense : line.split("\n")) {
                    if (sense.startsWith(":[")) {
                        senses.add(new Pair<>(Integer.parseInt(sense.substring(2, sense.indexOf("]"))),
                                sense.substring(sense.indexOf("]") + 1)));
                    } else if (sense.startsWith("::")) {
                        senses.get(senses.size() - 1)
                                .setRight(senses.get(senses.size() - 1).getRight() + "\n\t" + sense.substring(2));
                    }
                }

                return senses;

            }
        }

        return senses;

    }

    private HashMap<Integer, ArrayList<String>> getExamples(final String text) {
        String[] lines = text.split("\n\n");
        HashMap<Integer, ArrayList<String>> examples = new HashMap<>();

        for (String string : lines) {
            if (string.startsWith("{{Beispiele}}")) {
                int lastid = 0;
                for (String example : string.split("\n")) {
                    if (example.startsWith(":[")) {
                        String possibleId = example.substring(2, example.indexOf("]"));
                        Pair<Integer, String> subexample;
                        if (possibleId.matches("\\d+")) {
                            subexample = new Pair(Integer.parseInt(possibleId), "");
                        } else {
                            subexample = new Pair(Integer.parseInt(possibleId.split("[a-z]", 2)[0]),
                                    possibleId.split("\\d+", 2)[1]);
                        }

                        ArrayList<String> list = examples.getOrDefault(subexample.getLeft(), new ArrayList<>());

                        String prefix = "";
                        if (!subexample.getRight().isEmpty()) {
                            prefix = "[" + subexample.getRight() + "]: ";
                        }
                        list.add(prefix + example.substring(example.indexOf("]") + 1));

                        examples.put(subexample.getLeft(), list);
                        lastid = subexample.getLeft();

                    } else if (example.startsWith("::")) {
                        ArrayList<String> list = examples.getOrDefault(lastid, new ArrayList<>());
                        list.set(list.size() - 1, list.get(list.size() - 1).concat("\n\t" + example.substring(2)));
                        examples.put(lastid, list);
                    }
                }

                return examples;
            }

        }

        return examples;
    }

    private MediaWikiType convert(final Dictionary dictionary) throws JAXBException, IOException {
        final MediaWikiType mediaWiki = new MediaWikiType();

        mediaWiki.setLang("de");
        mediaWiki.setVersion("0.10");

        // Entries
        final List<DictionaryEntry> dictionaryEntries = dictionaryEntryRepository
                .findAllByIdDictionaryidDnameAndIdDictionaryidUname(dictionary.getId().getDname(),
                        dictionary.getId().getUname());

        for (final DictionaryEntry dictionaryEntry : dictionaryEntries) {

            final PageType page = new PageType();
            page.setTitle(dictionaryEntry.getHeadword());

            final RevisionType revision = new RevisionType();
            final ContributorType contributor = new ContributorType();
            final CommentType comment = new CommentType();
            contributor.setUsername(dictionary.getId().getUname());
            revision.setContributor(contributor);
            comment.setValue("Exported from PhiTag");
            revision.setComment(comment);

            final TextType text = new TextType();
            text.setValue(marshalEntry(dictionaryEntry));
            revision.setText(text);

            page.getRevisionOrUpload().add(revision);
            mediaWiki.getPage().add(page);
        }

        return mediaWiki;

    }

    private String marshalEntry(final DictionaryEntry entry) {
        String result = "=== {{Wortart|" + entry.getPartofspeech() + "|Deutsch}} ===\n\n";

        String senses = "{{Bedeutungen}}\n";
        String examples = "{{Beispiele}}\n";

        for (DictionaryEntrySense sense : entry.getSenses()) {
            senses += ":[" + sense.getSenseorder() + "] " + sense.getDefinition() + "\n";
            for (DictionaryEntrySenseExample example : sense.getExamples()) {
                examples += ":[" + sense.getSenseorder() + "] " + example.getExample() + "\n";
            }
        }

        result += senses + "\n" + examples;
        return result;

    }
}
