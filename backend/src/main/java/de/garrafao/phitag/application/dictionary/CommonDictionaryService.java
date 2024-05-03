package de.garrafao.phitag.application.dictionary;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryRepository;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryRepository;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleRepository;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseRepository;

/**
 * Service for common dictionary operations.
 */
public class CommonDictionaryService {
   
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
    public CommonDictionaryService(
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
     * Add a new entry to the dictionary.
     * 
     * @param entry      The entry to add
     */
    @Transactional
    public void addEntry(final DictionaryEntry entry) {
        this.dictionaryEntryRepository.save(entry);
    }

    /**
     * Add a new sense to the dictionary entry.
     * 
     * @param sense The sense to add
     */
    @Transactional
    public void addSense(final DictionaryEntrySense sense) {
        this.dictionaryEntrySenseRepository.save(sense);
    }

    /**
     * Add a new example to the dictionary entry sense.
     * 
     * @param example The example to add
     */
    @Transactional
    public void addExample(final DictionaryEntrySenseExample example) {
        this.dictionaryEntrySenseExampleRepository.save(example);
    }
}
