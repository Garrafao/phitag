package de.garrafao.phitag.infrastructure.rest.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.dictionary.entry.DictionaryEntryApplicationService;
import de.garrafao.phitag.application.dictionary.entry.data.PagedDictionaryEntryDto;

@RestController
@RequestMapping(value = "/api/v1/dictionary/entry")
public class DictionaryEntryResource {
    
    private DictionaryEntryApplicationService dictionaryEntryApplicationService;

    @Autowired
    public DictionaryEntryResource(DictionaryEntryApplicationService dictionaryEntryApplicationService) {
        this.dictionaryEntryApplicationService = dictionaryEntryApplicationService;
    }

    /**
     * Query dictionary entries by dictionary.
     * Pagesize is 10 by default.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname The name of the dictionary
     * @param uname The username of the dictionary owner (currently will be ignored and replaced by the requesting user)
     * @param headword The headword of the dictionary entry (optional)
     * @param partofspeech The part of speech of the dictionary entry (optional)
     * @param page The page number (optional) default: 0
     * @return A paginated list of dictionary entries
     */
    @GetMapping()
    public PagedDictionaryEntryDto byDictionary(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "headword", required = false) final String headword,
            @RequestParam(value = "partofspeech", required = false) final String partofspeech,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page) {
        return dictionaryEntryApplicationService.byDictionary(authenticationToken, dname, uname, headword, partofspeech, page);
    }

    /**
     * Create a new dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname The name of the dictionary
     * @param uname The username of the dictionary owner (currently will be ignored and replaced by the requesting user)
     * @param headword The headword of the dictionary entry
     * @param partofspeech The part of speech of the dictionary entry (optional)
     */
    @PostMapping("/create")
    public void create(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "headword") final String headword,
            @RequestParam(value = "partofspeech", required = false) final String partofspeech) {
        dictionaryEntryApplicationService.create(authenticationToken, dname, uname, headword, partofspeech);
    }

    /**
     * Update a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param id The id of the dictionary entry
     * @param uname The username of the dictionary owner (currently will be ignored and replaced by the requesting user)
     * @param dname The name of the dictionary
     * @param headword The headword of the dictionary entry (optional)
     * @param partofspeech The part of speech of the dictionary entry (optional)
     */
    @PostMapping("/update")
    public void update(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "id") final String id,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "headword", required = false) final String headword,
            @RequestParam(value = "partofspeech", required = false) final String partofspeech) {
        dictionaryEntryApplicationService.update(authenticationToken, id, dname, uname, headword, partofspeech);
    }

    /**
     * Delete a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param id The id of the dictionary entry
     * @param dname The name of the dictionary
     * @param uname The username of the dictionary owner (currently will be ignored and replaced by the requesting user)
     */
    @PostMapping("/delete")
    public void delete(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "id") final String id,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname) {
        dictionaryEntryApplicationService.delete(authenticationToken, id, dname, uname);
    }

}
