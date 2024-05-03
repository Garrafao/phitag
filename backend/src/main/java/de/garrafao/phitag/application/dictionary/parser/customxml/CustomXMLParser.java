package de.garrafao.phitag.application.dictionary.parser.customxml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import de.garrafao.phitag.application.dictionary.parser.customxml.data.generated.*;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryRepository;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryRepository;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleRepository;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseRepository;

@Service
public class CustomXMLParser implements IDictionaryParser {

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
    public CustomXMLParser(
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
     * Parse a dictionary file and save it to the database.
     */
    @Override
    @Transactional
    public void parse(Dictionary dictionary, MultipartFile file) {
        ParseDictionary parseDictionary;
        try (InputStream inputStream = file.getInputStream()) {
            parseDictionary = unmarshal(file);
        } catch (IOException | JAXBException e) {
            throw new IllegalArgumentException("Dictionary file type not supported");
        }

        List<DictionaryEntry> dictionaryEntries = new ArrayList<>();
        List<DictionaryEntrySense> dictionaryEntrySenses = new ArrayList<>();
        List<DictionaryEntrySenseExample> dictionaryEntrySenseExamples = new ArrayList<>();

        for (EntryType entryType : parseDictionary.getEntries().getEntry()) {
            final DictionaryEntry dictionaryEntry = constructDictionaryEntryFromEntryType(dictionary, entryType);

            dictionaryEntries.add(dictionaryEntry);

            int senseOrder = 0;
            for (SenseType senseType : entryType.getSenses().getSense()) {
                final DictionaryEntrySense dictionaryEntrySense = constructDictionaryEntrySenseFromEntryType(
                        dictionaryEntry, senseType, senseOrder);

                dictionaryEntrySenses.add(dictionaryEntrySense);

                int exampleOrder = 0;
                for (String example : senseType.getExamples().getExample()) {
                    final DictionaryEntrySenseExample dictionaryEntrySenseExample = constructDictionaryEntrySenseExampleFromEntryType(
                            dictionaryEntrySense, example, exampleOrder);

                    dictionaryEntrySenseExamples.add(dictionaryEntrySenseExample);

                    exampleOrder++;
                }

                senseOrder++;
            }

        }

        dictionaryEntryRepository.saveAll(dictionaryEntries);
        dictionaryEntrySenseRepository.saveAll(dictionaryEntrySenses);
        dictionaryEntrySenseExampleRepository.saveAll(dictionaryEntrySenseExamples);
    }

    private ParseDictionary unmarshal(MultipartFile file) throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ParseDictionary.class);
        return (ParseDictionary) jaxbContext.createUnmarshaller().unmarshal(file.getInputStream());
    }

    private DictionaryEntry constructDictionaryEntryFromEntryType(final Dictionary dictionary,
            final EntryType entryType) {
        return new DictionaryEntry(dictionary, entryType.getHeadword(), entryType.getPartOfSpeech());
    }

    private DictionaryEntrySense constructDictionaryEntrySenseFromEntryType(final DictionaryEntry dictionaryEntry,
            final SenseType senseType, final int order) {
        return new DictionaryEntrySense(dictionaryEntry, senseType.getDefinition(), order);
    }

    private DictionaryEntrySenseExample constructDictionaryEntrySenseExampleFromEntryType(
            final DictionaryEntrySense dictionaryEntrySense, final String example, final int order) {
        return new DictionaryEntrySenseExample(dictionaryEntrySense, example, order);
    }

    @Override
    public DictionaryFileType getFileType() {
        return DictionaryFileType.CUSTOM_XML;
    }

    @Override
    public InputStreamResource export(Dictionary dictionary) {
        try {
            final ParseDictionary parseDictionary = marshal(dictionary);

            final JAXBContext jaxbContext = JAXBContext.newInstance(ParseDictionary.class);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            marshaller.marshal(parseDictionary, byteArrayOutputStream);

            return new InputStreamResource(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        } catch (JAXBException | IOException e) {
            throw new IllegalArgumentException("Dictionary file type not supported");
        }

    }

    public ParseDictionary marshal(final Dictionary dictionary) throws JAXBException, IOException {
        final ParseDictionary parseDictionary = new ParseDictionary();
        
        // Info
        final InfoType infoType = new InfoType();
        infoType.setAuthor(dictionary.getId().getUname());
        infoType.setName(dictionary.getId().getDname());
        infoType.setDescription(dictionary.getDescription());

        // Entries
        final List<DictionaryEntry> dictionaryEntries = dictionaryEntryRepository
                .findAllByIdDictionaryidDnameAndIdDictionaryidUname(dictionary.getId().getDname(), dictionary.getId().getUname());

        final EntriesType entriesType = new EntriesType();

        for (DictionaryEntry dictionaryEntry : dictionaryEntries) {
            final EntryType entryType = new EntryType();
            entryType.setHeadword(dictionaryEntry.getHeadword());
            entryType.setPartOfSpeech(dictionaryEntry.getPartofspeech());

            // Senses
            final SensesType sensesType = new SensesType();

            for (DictionaryEntrySense dictionaryEntrySense : dictionaryEntry.getSenses()) {
                final SenseType senseType = new SenseType();
                senseType.setDefinition(dictionaryEntrySense.getDefinition());

                // Examples
                final ExamplesType examplesType = new ExamplesType();

                for (DictionaryEntrySenseExample dictionaryEntrySenseExample : dictionaryEntrySense.getExamples()) {
                    examplesType.getExample().add(dictionaryEntrySenseExample.getExample());
                }

                senseType.setExamples(examplesType);
                sensesType.getSense().add(senseType);
            }

            entryType.setSenses(sensesType);
            entriesType.getEntry().add(entryType);
        }

        parseDictionary.setInfo(infoType);
        parseDictionary.setEntries(entriesType);

        return parseDictionary;
    }

}
