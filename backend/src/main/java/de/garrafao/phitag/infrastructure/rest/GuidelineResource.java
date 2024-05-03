package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.guideline.GuidelineApplicationService;
import de.garrafao.phitag.application.guideline.data.GuidelineDto;

@RestController
@RequestMapping(value = "/api/v1/guideline")
public class GuidelineResource {

    private final GuidelineApplicationService guidelineApplicationService;

    @Autowired
    public GuidelineResource(final GuidelineApplicationService guidelineApplicationService) {
        this.guidelineApplicationService = guidelineApplicationService;
    }

    /**
     * Get a specific guideline of a project.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param owner               The owner of the project
     * @param project             The name of the project
     * @param guideline           The name of the guideline
     * @return The guideline
     */
    @GetMapping()
    public GuidelineDto getGuideline(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam(value = "guideline") final String guideline) {
        return this.guidelineApplicationService.getGuideline(authenticationToken, owner, project, guideline);
    }

    /**
     * Get all guidelines of a project.
     * 
     * TODO: Merge with getGuideline()?
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param owner               The owner of the project
     * @param project             The name of the project
     * @return The guidelines
     */
    @GetMapping(value = "/by-project")
    public List<GuidelineDto> getGuidelinesDtoByProject(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project) {
        return this.guidelineApplicationService.getGuidelineDtosOfProject(authenticationToken, owner, project);
    }

    /**
     * Create a new guideline for a project.
     * 
     * @param authenticationToken The authentication token of the requesting user
     * @param owner               The owner of the project
     * @param project             The name of the project
     * @param guideline           The name of the guideline
     * @param file                The guideline file
     */
    @PostMapping(path = "/create")
    public void create(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestParam(value = "owner") final String owner,
            @RequestParam(value = "project") final String project,
            @RequestParam("guideline") final MultipartFile file) {
        this.guidelineApplicationService.create(authenticationToken, owner, project, file);
    }
}
