package de.garrafao.phitag.infrastructure.rest.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.dictionary.example.DictionaryEntrySenseExampleApplicationService;

@RestController
@RequestMapping(value = "/api/v1/dictionary/entry/sense/example")
public class DictionaryEntrySenseExampleResource {

    private final DictionaryEntrySenseExampleApplicationService dictionaryEntrySenseExampleApplicationService;

    @Autowired
    public DictionaryEntrySenseExampleResource(
            DictionaryEntrySenseExampleApplicationService dictionaryEntrySenseExampleApplicationService) {
        this.dictionaryEntrySenseExampleApplicationService = dictionaryEntrySenseExampleApplicationService;
    }

    /**
     * Create a new example for a sense of a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param senseid             The id of the sense
     * @param entryid             The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     * 
     * @param example             The example of the sense
     * @param order               The order of the sense
     */
    @PostMapping("/create")
    public void create(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "senseid") final String senseid,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "example") final String example,
            @RequestParam(value = "order") final Integer order) {
        dictionaryEntrySenseExampleApplicationService.create(authenticationToken, senseid, entryid, dname, uname,
                example,
                order);
    }

    /**
     * Update an example for a sense of a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param exampleid           The id of the example
     * @param senseid             The id of the sense
     * @param entryid             The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     * 
     * @param example             The example of the sense
     * @param order               The order of the sense
     */
    @PostMapping("/update")
    public void update(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "exampleid") final String exampleid,
            @RequestParam(value = "senseid") final String senseid,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname,
            @RequestParam(value = "example") final String example,
            @RequestParam(value = "order") final Integer order) {
        dictionaryEntrySenseExampleApplicationService.update(authenticationToken, exampleid, senseid, entryid, dname,
                uname,
                example, order);
    }

    /**
     * Delete an example for a sense of a dictionary entry.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param exampleid           The id of the example
     * @param senseid             The id of the sense
     * @param entryid             The id of the dictionary entry
     * @param uname               The username of the dictionary owner (currently
     *                            will be ignored and replaced by the requesting
     *                            user)
     * @param dname               The name of the dictionary
     */
    @PostMapping("/delete")
    public void delete(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "exampleid") final String exampleid,
            @RequestParam(value = "senseid") final String senseid,
            @RequestParam(value = "entryid") final String entryid,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "uname") final String uname) {
        dictionaryEntrySenseExampleApplicationService.delete(authenticationToken, exampleid, senseid, entryid, dname,
                uname);
    }

}
