package de.garrafao.phitag.application.dictionary.entry;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.dictionary.entry.data.PagedDictionaryEntryDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntryRepository;
import de.garrafao.phitag.domain.dictionary.entry.query.DictionaryEntryQueryBuilder;
import de.garrafao.phitag.domain.user.User;

@Service
public class DictionaryEntryApplicationService {

    // Repository

    private final DictionaryEntryRepository dictionaryEntryRepository;

    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    public DictionaryEntryApplicationService(
            final DictionaryEntryRepository dictionaryEntryRepository,
            final CommonService commonService,
            final ValidationService validationService) {
        this.dictionaryEntryRepository = dictionaryEntryRepository;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Returns a page of entries for the given dictionary.
     * The size of the page is 10.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname               The dictionary to get the entries for
     * @param uname               The user to get the entries for (optional,
     *                            defaults currently to requesting user)
     * @param headword            The headword to filter for (optional)
     * @param partOfSpeech        The part of speech to filter for (optional)
     * @param page                The page to get (optional, defaults to 0)
     * @return
     */
    public PagedDictionaryEntryDto byDictionary(final String authenticationToken, final String dname,
            final String uname,
            final String headword, final String partOfSpeech, final Integer page) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Query query = new DictionaryEntryQueryBuilder()
                .withDictionaryname(dname)
                .withOwner(requester.getUsername())
                .withHeadword(headword)
                .withPartOfSpeach(partOfSpeech)
                .build();

        Page<DictionaryEntry> entries = this.dictionaryEntryRepository.findAllByQuery(query, PageRequest.of(page, 7, Sort.by("headword").ascending()));

        return PagedDictionaryEntryDto.from(entries);
    }

    /**
     * Creates a new entry for the given dictionary.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname               The dictionary to create the entry for
     * @param uname               The user to create the entry for (optional,
     *                            defaults currently to requesting user)
     * @param headword            The headword of the entry
     * @param partOfSpeech        The part of speech of the entry
     */
    @Transactional
    public void create(final String authenticationToken, final String dname, final String uname, final String headword,
            final String partOfSpeech) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Dictionary dictionary = this.commonService.getDictionary(dname, requester.getUsername());

        final DictionaryEntry entry = new DictionaryEntry(dictionary, headword, partOfSpeech);

        this.dictionaryEntryRepository.save(entry);
    }

    /**
     * Updates the entry with the given id.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param id                  The id of the entry to update
     * @param dname               The dictionary to update the entry for
     * @param uname               The user to update the entry for (optional,
     *                            defaults currently to requesting user)
     * @param headword            The headword of the entry
     * @param partOfSpeech        The part of speech of the entry
     */
    @Transactional
    public void update(final String authenticationToken, final String id, final String dname, final String uname,
            final String headword, final String partOfSpeech) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntry entry = this.commonService.getEntry(id, dname, requester.getUsername());

        validateEntry(entry, headword, partOfSpeech);

        entry.setHeadword(headword);
        entry.setPartofspeech(partOfSpeech);

        this.dictionaryEntryRepository.save(entry);
    }

    private void validateEntry(final DictionaryEntry entry, final String headword, final String partOfSpeech) {
        if (headword.isBlank()) {
            throw new IllegalArgumentException("Headword must not be blank");
        }

        if (partOfSpeech.isBlank()) {
            throw new IllegalArgumentException("Part of speech must not be blank");
        }
    }

    /**
     * Deletes the entry with the given id.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param id                  The id of the entry to delete
     * @param dname               The dictionary to delete the entry for
     * @param uname               The user to delete the entry for (optional,
     *                            defaults currently to requesting user)
     */
    public void delete(final String authenticationToken, final String id, final String dname, final String uname) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntry entry = this.commonService.getEntry(id, dname, requester.getUsername());

        this.dictionaryEntryRepository.delete(entry);
    }

}
