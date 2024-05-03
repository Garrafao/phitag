package de.garrafao.phitag.application.dictionary.example;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExample;
import de.garrafao.phitag.domain.dictionary.example.DictionaryEntrySenseExampleRepository;
import de.garrafao.phitag.domain.dictionary.sense.DictionaryEntrySense;
import de.garrafao.phitag.domain.user.User;

@Service
public class DictionaryEntrySenseExampleApplicationService {

    // Repository

    private final DictionaryEntrySenseExampleRepository repository;

    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    public DictionaryEntrySenseExampleApplicationService(
            final DictionaryEntrySenseExampleRepository repository,

            final CommonService commonService,
            final ValidationService validationService) {
        this.repository = repository;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Creates a new example for the given sense.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseId             The sense to create the example for
     * @param entryId             The entry to create the example for
     * @param dname               The dictionary to create the example for
     * @param uname               The user to create the example for (optional,
     *                            currently defaults to requesting user)
     * @param example             The example to create
     * @param order               The order of the example
     */
    @Transactional
    public void create(final String authenticationToken,
            final String senseId, final String entryId, final String dname, final String uname,
            final String example, final Integer order) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntrySense sense = this.commonService.getSenseEntry(senseId, entryId, dname,
                requester.getUsername());

        final DictionaryEntrySenseExample exampleEntity = new DictionaryEntrySenseExample(sense, example, order);

        this.repository.save(exampleEntity);

    }

    /**
     * Updates the given example.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param exampleId           The example to update
     * @param senseId             The sense to update the example for
     * @param entryId             The entry to update the example for
     * @param dname               The dictionary to update the example for
     * @param uname               The user to update the example for (optional,
     *                            currently defaults to requesting user)
     * @param example             The new example text
     * @param order               The new order of the example
     */
    @Transactional
    public void update(final String authenticationToken, final String exampleId, final String senseId,
            final String entryId, final String dname,
            final String uname, final String example, final Integer order) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntrySenseExample exampleEntity = this.commonService.getSenseExample(exampleId, senseId,
                entryId, dname, requester.getUsername());

        exampleEntity.setExample(example);
        exampleEntity.setExampleorder(order);

        this.repository.save(exampleEntity);

    }

    /**
     * Deletes the given example.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param exampleId           The example to delete
     * @param senseId             The sense to delete the example for
     * @param entryId             The entry to delete the example for
     * @param uname               The user to delete the example for (optional,
     * @param dname               The dictionary to delete the example for
     * 
     */
    @Transactional
    public void delete(final String authenticationToken, final String exampleId, final String senseId,
            final String entryId, final String dname, final String uname) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final DictionaryEntrySenseExample exampleEntity = this.commonService.getSenseExample(exampleId, senseId,
                entryId, dname, requester.getUsername());

        this.repository.delete(exampleEntity);
    }

}
