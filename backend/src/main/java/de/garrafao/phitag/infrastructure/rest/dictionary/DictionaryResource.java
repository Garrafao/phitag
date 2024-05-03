package de.garrafao.phitag.infrastructure.rest.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.dictionary.dictionary.DictionaryApplicationService;
import de.garrafao.phitag.application.dictionary.dictionary.data.PagedDictionaryDto;

@RestController
@RequestMapping(value = "/api/v1/dictionary")
public class DictionaryResource {

    private DictionaryApplicationService dictionaryApplicationService;

    @Autowired
    public DictionaryResource(DictionaryApplicationService dictionaryApplicationService) {
        this.dictionaryApplicationService = dictionaryApplicationService;
    }

    /**
     * Get all dictionaries of requesting user as a paginated list.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param uname               The user to get the dictionaries for (optional,
     *                            defaults currently to requesting user)\
     * @param page                The page to get (optional, defaults to 0)
     * @return A paginated list of dictionaries
     */
    @GetMapping()
    public PagedDictionaryDto byUser(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "uname", required = false) final String uname,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {
        return dictionaryApplicationService.all(authenticationToken, uname, page);
    }

    /**
     * Create a new dictionary.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname               The name of the dictionary
     * @param description         The description of the dictionary
     * @param file                The file of the dictionary (optional)
     */
    @PostMapping(value = "/create")
    public void create(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "description") final String description,
            @RequestParam(value = "filetype", required = false) final String filetype,
            @RequestParam(value = "file", required = false) final MultipartFile file) {
        dictionaryApplicationService.create(authenticationToken, dname, description, filetype, file);
    }

    /**
     * Export a dictionary.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param dname               The name of the dictionary to export
     * @param filetype            The filetype to export to
     */
    @GetMapping(value = "/export")
    public ResponseEntity<Resource> export(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "dname") final String dname,
            @RequestParam(value = "filetype") final String filetype) {
        InputStreamResource streamResource = dictionaryApplicationService.export(authenticationToken, dname, filetype);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=dictionary.xml");
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, "application/xml");

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
    }

}
