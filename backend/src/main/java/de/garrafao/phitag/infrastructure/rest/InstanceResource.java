package de.garrafao.phitag.infrastructure.rest;

import de.garrafao.phitag.application.instance.InstanceApplicationService;
import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.data.PagedInstanceDto;
import de.garrafao.phitag.application.instance.wssimtag.data.WSSIMTagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/instance")
public class InstanceResource {

    private static final String TEXT_CSV = "text/csv";

    private final InstanceApplicationService instanceApplicationService;

    @Autowired
    public InstanceResource(InstanceApplicationService instanceApplicationService) {
        this.instanceApplicationService = instanceApplicationService;
    }

    /**
     * Get instance dtos of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * - If the phase is a tutorial, only the owner and admins can see the phase +
     * data
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param additional
     *                            Additional Data (e.g. WSSIM -> sense)
     * @return
     *         Phase data
     */
    @GetMapping()
    public List<IInstanceDto> getInstanceDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "additional", required = false, defaultValue = "false") final boolean additional) {
        return this.instanceApplicationService.getInstanceDtos(authenticationToken, owner, project, phase, additional);
    }

    /**
     * Get instance dtos of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, or
     * - Project must be public
     * - If project is not active, only the owner can see the phases
     * - If the phase is a tutorial, only the owner and admins can see the phase +
     * data
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param additional
     *                            Additional Data (e.g. WSSIM -> sense)
     * @return
     *         Phase data
     */
    @GetMapping("/paged")
    public PagedInstanceDto getPagedInstanceDtos(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "additional", required = false, defaultValue = "false") final boolean additional,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page) {
        return this.instanceApplicationService.getPagedInstanceDto(authenticationToken, owner, project, phase,
                additional, page, 50, "");
    }

    /**
     * Get a random instance dto of a specific phase.
     * Underlying DTO depends on the annotation type of the phase and should be
     * resolved by the client
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project, and
     * - Phase must not be a tutorial
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @return
     *         Phase data
     */
    @GetMapping(value = "/random")
    public IInstanceDto getRandomInstanceDto(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase) {
        return this.instanceApplicationService.getAnnotationInstance(authenticationToken, owner,
                project, phase);
    }

    /**
     * Export instance data of a specific phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be an annotator in the project with admin rights, and
     * - If project is not active, only the owner can see the phases
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param additional
     *                            Additional Data (e.g. WSSIM -> sense)
     * @return
     *         Data as CSV
     */
    @GetMapping(value = "/export", produces = TEXT_CSV)
    public ResponseEntity<Resource> exportInstance(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "additional", required = false, defaultValue = "false") final boolean additional) {
        InputStreamResource streamResource = this.instanceApplicationService.exportInstance(authenticationToken, owner,
                project, phase, additional);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=instances.tsv");
        // defining the custom Content-Type
        headers.set(HttpHeaders.CONTENT_TYPE, TEXT_CSV);

        return new ResponseEntity<>(
                streamResource,
                headers,
                HttpStatus.OK);
    }

    /**
     * Add instance data to a phase.
     * 
     * The requesting user must fulfill the following conditions:
     * - Be the owner of the project or an admin
     * - Project has to be active
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param additional
     *                            Additional Data (e.g. WSSIM -> sense)
     * @param file
     *                            The instancedata to add
     */
    @PostMapping()
    public void addInstance(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "additional", required = false, defaultValue = "false") final boolean additional,
            @RequestParam(value = "file") final MultipartFile file) {
        this.instanceApplicationService.addInstances(authenticationToken, owner, project, phase, additional, file);
    }

    /**
     * Generate instance data for a phase from usages associated with the project.
     * 
     * @param authenticationToken
     *                            The authentication token of the requesting user
     * @param owner
     *                            The owner of the project
     * @param project
     *                            The name of the project
     * @param phase
     *                            The name of the phase
     * @param labels
     *                            The labels set for the instances
     * @param nonLabel
     *                            Additional Data (e.g. WSSIM -> sense)
     * @param file
     *                            The instancedata to add
     */
    @PostMapping(value = "/generate")
    public void generateInstance(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "labels") final String labels,
            @RequestParam(value = "nonLabel") final String nonLabel,
            @RequestParam(value = "file", required = false) final MultipartFile file) {
        // clean up labels
        List<String> labelsList = Arrays.asList(labels.split(","));
        labelsList = labelsList.stream().map(String::trim).collect(Collectors.toList());

        this.instanceApplicationService.generateInstances(authenticationToken, owner, project, phase, labelsList,
                nonLabel,
                file);
    }

    // Custom Functionality for certain Tasks/Instances

    /**
     * Get the WSSIM Tags for a specific lemma
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param owner               The owner of the project
     * @param project             The name of the project
     * @param phase               The name of the phase
     * @param lemma               The lemma in question
     * @return WSSIM Tags with same lemma for this phase
     */
    @GetMapping(value = "/wssimtag-lemma")
    public List<WSSIMTagDto> getWssimTagsOfLemma(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase,
            @RequestParam(value = "lemma") final String lemma) {
        return this.instanceApplicationService.getWssimTagsByLemma(authenticationToken, owner, project, phase, lemma);
    }


    @GetMapping(value = "/count-allocated-instance")
    public int countAllocatedInstance(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "phase") final String phase){
        return this.instanceApplicationService.countALlocatedInstance(authenticationToken, owner, project, phase);
    }

}
