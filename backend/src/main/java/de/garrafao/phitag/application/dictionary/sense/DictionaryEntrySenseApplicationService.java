package de.garrafao.phitag.application.dictionary.sense;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.entry.DictionaryEntry;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySenseRepository;
import de.garrafao.phitag.domain.user.User;

@Service
public class DictionaryEntrySenseApplicationService {

    // Repository

    private final DictionaryEntrySenseRepository repository;

    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    public DictionaryEntrySenseApplicationService(
            final DictionaryEntrySenseRepository repository,

            final CommonService commonService,
            final ValidationService validationService) {
        this.repository = repository;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Creates a new sense for the given entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param entryid             The id of the sense to create
     * @param dname               The dictionary to create the sense for
     * @param uname               The user to create the sense for (optional,
     *                            currently defaults to requesting user)
     * @param definition          The definition of the sense
     * @param order               The order of the sense
     */
    @Transactional
    public void create(final String authenticationToken, final String entryid, final String dname, final String uname,
            final String definition,
            final Integer order) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntry entry = this.commonService.getEntry(entryid, dname, requester.getUsername());

        final DictionaryEntrySense senseEntity = new DictionaryEntrySense(entry, definition, order < 0 ? entry.getSenses().size() : order);

        this.repository.save(senseEntity);
    }

    /**
     * Updates the given sense.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseId             The id of the sense to update
     * @param entryid             The id of the entry to update
     * @param uname               The user to update the sense for (optional,
     *                            currently defaults to requesting user)
     * @param dname               The dictionary to update the sense for
     * @param definition          The new definition of the sense
     * @param order               The new order of the sense
     */
    @Transactional
    public void update(final String authenticationToken, final String senseId, final String entryid, final String dname,
            final String uname,
            final String definition, final Integer order) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntrySense sense = this.commonService.getSenseEntry(senseId, entryid, dname,
                requester.getUsername());

        sense.setDefinition(definition);
        sense.setSenseorder(order);

        this.repository.save(sense);
    }

    /**
     * Deletes the given sense.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseId             The id of the sense to delete
     * @param entryid             The id of the entry to delete
     * @param uname               The user to delete the sense for (optional,
     *                            currently defaults to requesting user)
     * @param dname               The dictionary to delete the sense for
     */

    @Transactional
    public void delete(final String authenticationToken, final String senseId, final String entryid, final String dname,
            final String uname) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntrySense sense = this.commonService.getSenseEntry(senseId, entryid, dname,
                requester.getUsername());

        this.repository.delete(sense);
    }

}
