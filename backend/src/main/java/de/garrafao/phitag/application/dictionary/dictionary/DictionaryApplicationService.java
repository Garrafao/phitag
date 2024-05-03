package de.garrafao.phitag.application.dictionary.dictionary;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.dictionary.dictionary.data.DictionaryFileType;
import de.garrafao.phitag.application.dictionary.dictionary.data.PagedDictionaryDto;
import de.garrafao.phitag.application.dictionary.parser.DictionaryParserFactory;
import de.garrafao.phitag.application.dictionary.parser.IDictionaryParser;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.dictionary.dictionary.Dictionary;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryId;
import de.garrafao.phitag.domain.dictionary.dictionary.DictionaryRepository;
import de.garrafao.phitag.domain.user.User;

@Service
public class DictionaryApplicationService {

    // Repository

    private final DictionaryRepository dictionaryRepository;

    // Parser

    private final DictionaryParserFactory dictionaryParserFactory;

    // Common services

    private final CommonService commonService;

    private final ValidationService validationService;

    // Other

    @Autowired
    public DictionaryApplicationService(
            final DictionaryRepository dictionaryRepository,

            final DictionaryParserFactory dictionaryParserFactory,

            final CommonService commonService,
            final ValidationService validationService) {
        this.dictionaryRepository = dictionaryRepository;

        this.dictionaryParserFactory = dictionaryParserFactory;

        this.commonService = commonService;
        this.validationService = validationService;

    }

    /**
     * Returns all dictionaries for the given user.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param uname               The user to get the dictionaries for (optional,
     *                            defaults currently to requesting user)
     * @param page                The page to get (optional, defaults to 0)
     * @return
     */
    public PagedDictionaryDto all(final String authenticationToken, final String uname, final int page) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Pageable pageable = PageRequest.of(page, 50, Sort.by("id.uname").ascending());

        return PagedDictionaryDto.from(
                this.dictionaryRepository.findAllByIdUname(requester.getUsername(), pageable));

    }

    /**
     * Create a new dictionary.
     * If the file is not null, it will be parsed and the dictionary will be created
     * from it.
     * TODO: Need to extend this and give a selection of formats to parse from
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param name                The name of the dictionary
     * @param description         The description of the dictionary
     * @param file                The file of the dictionary (optional)
     */
    @Transactional
    public void create(final String authenticationToken, final String name, final String description, final String filetype,
            final MultipartFile file) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        // Check if dictionary with name already exists
        if (this.dictionaryRepository.findById(new DictionaryId(name, requester.getUsername())).isPresent()) {
            throw new IllegalArgumentException("Dictionary with name already exists");
        }

        Dictionary dictionary = this.dictionaryRepository.save(new Dictionary(name, requester, description));

        if (file == null) {
            return;
        }

        if (filetype == null || filetype.isBlank() || !DictionaryFileType.isValid(filetype)) {
            throw new IllegalArgumentException("Filetype must be set if no file is given");
        }

        final IDictionaryParser parser = dictionaryParserFactory.getParser(DictionaryFileType.fromName(filetype));

        parser.parse(dictionary, file);
    }

    /**
     * Export a dictionary.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param name                The name of the dictionary
     * @param filetype            The filetype to export to
     * @return
     */
    public InputStreamResource export(final String authenticationToken, final String name, final String filetype) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Dictionary dictionary = this.dictionaryRepository.findById(new DictionaryId(name, requester.getUsername()))
                .orElseThrow(() -> new IllegalArgumentException("Dictionary not found"));

        if (filetype == null || filetype.isBlank() || !DictionaryFileType.isValid(filetype)) {
            throw new IllegalArgumentException("Filetype must be set if no file is given");
        }

        final IDictionaryParser parser = dictionaryParserFactory.getParser(DictionaryFileType.fromName(filetype));

        return parser.export(dictionary);
    }


}
