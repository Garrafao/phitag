package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.phitagdata.PhitagDataApplicationService;
import de.garrafao.phitag.application.phitagdata.usage.data.EditUsageCommand;
import de.garrafao.phitag.application.phitagdata.usage.data.PagedUsageDto;
import de.garrafao.phitag.application.phitagdata.usage.data.UsageDto;

@RestController
@RequestMapping(value = "/api/v1/phitagdata")
public class PhitagDataResource {

    private static final String TEXT_CSV = "text/csv";
    private final PhitagDataApplicationService phitagDataApplicationService;

    @Autowired
    public PhitagDataResource(PhitagDataApplicationService phitagDataApplicationService) {
        this.phitagDataApplicationService = phitagDataApplicationService;
    }

    /**
     * Get usages of a specific project.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The name of the project
     * @return
     *         Phase data
     */
    @GetMapping(value = "/usage")
    public List<UsageDto> getUsageDto(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return this.phitagDataApplicationService.getUsageDto(authenticationToken, owner, project);
    }

        /**
     * Get usages of a specific project.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The name of the project
     * @return
     *         Phase data
     */
    @GetMapping(value = "/usage/paged")
    public PagedUsageDto getPagedUsageDto(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "page") final int page) {
        return this.phitagDataApplicationService.getPagedUsageDto(authenticationToken, owner, project, 50, page, "id.dataid");
    }


    /**
     * Export phase data of a specific phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project with admin rights, and
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The name of the phase
     * @return
     *         Data as CSV
     */
    @GetMapping(value = "/export", produces = TEXT_CSV)
    public ResponseEntity<Resource> exportUsage(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        InputStreamResource streamResource = this.phitagDataApplicationService.exportUsages(authenticationToken, owner,
                project);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=usages.tsv");
        headers.set(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
    }

    /**
     * Add phasedata to a project.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param project
     *                            The name of the project
     * @param file
     *                            The usage to add
     */
    @PostMapping(value = "/usage")
    public void addUsages(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "file") final MultipartFile file) {
        this.phitagDataApplicationService.addUsages(authenticationToken, owner, project, file);
    }

    /**
     * Edit a usage.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * 
     * @param authenticationToken
     *                           The authentication token of the requesting user
     * @param command
     *                          The command to edit the usage
     */
    @PostMapping(value = "/usage/edit")
    public void editUsage(
            @RequestHeader("Authorization") final String authenticationToken,
            @RequestBody() final EditUsageCommand command) {
        this.phitagDataApplicationService.editUsage(authenticationToken, command);
    }

}
