package de.garrafao.phitag.infrastructure.rest.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.dictionary.sense.DictionaryEntrySenseApplicationService;

@RestController
@RequestMapping(value = "/api/v1/dictionary/entry/sense")
public class DictionaryEntrySenseResource {

    private DictionaryEntrySenseApplicationService dictionaryEntrySenseApplicationService;

    @Autowired
    public DictionaryEntrySenseResource(DictionaryEntrySenseApplicationService dictionaryEntrySenseApplicationService) {
        this.dictionaryEntrySenseApplicationService = dictionaryEntrySenseApplicationService;
    }

    /**
     * Create a new sense for a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param entryid             The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     * 
     * @param definition          The definition of the sense
     * @param order               The order of the sense
     * 
     */
    @PostMapping("/create")
    public void create(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "definition") final String definition,
            @RequestParam(value = "order") final Integer order) {
        dictionaryEntrySenseApplicationService.create(authenticationToken, entryid, dname, uname, definition, order);
    }

    /**
     * Update a sense for a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseid             The id of the sense
     * @param entryid             The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     * 
     * @param definition          The definition of the sense
     * @param order               The order of the sense
     */
    @PostMapping("/update")
    public void update(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "senseid") final String senseid,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "definition") final String definition,
            @RequestParam(value = "order") final Integer order) {
        dictionaryEntrySenseApplicationService.update(authenticationToken, senseid, entryid, dname, uname, definition,
                order);
    }

    /**
     * Delete a sense for a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseid             The id of the sense
     * @param id                  The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     */
    @PostMapping("/delete")
    public void delete(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "senseid") final String senseid,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname) {
        dictionaryEntrySenseApplicationService.delete(authenticationToken, senseid, entryid, dname, uname);
    }

}
