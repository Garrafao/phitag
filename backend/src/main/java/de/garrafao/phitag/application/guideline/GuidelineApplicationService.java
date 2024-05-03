package de.garrafao.phitag.application.guideline;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.guideline.data.GuidelineDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.guideline.Guideline;
import de.garrafao.phitag.domain.guideline.GuidelineRepository;
import de.garrafao.phitag.domain.guideline.error.GuidelineNotFoundException;
import de.garrafao.phitag.domain.guideline.error.GuidelineParseException;
import de.garrafao.phitag.domain.guideline.query.GuidelineQueryBuilder;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Service
public class GuidelineApplicationService {

    // Repository

    private final GuidelineRepository guidelineRepository;

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public GuidelineApplicationService(final GuidelineRepository guidelineRepository,
            final CommonService commonService,
            final ValidationService validationService) {
        this.guidelineRepository = guidelineRepository;
        this.commonService = commonService;
        this.validationService = validationService;
    }

    // Getter

    /**
     * Returns the guideline with the given name.
     *
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @param guideline           the name of the guideline
     * 
     * @return {@link GuidelineDto} of the guideline
     */
    public GuidelineDto getGuideline(final String authenticationToken, final String owner, final String project,
            final String guideline) {
        final User requester = commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = commonService.getProject(owner, project);

        validationService.projectAccess(requester, projectEntity);

        final Guideline guidelineEntity = this.guidelineRepository
                .findByIdNameAndIdProjectidNameAndIdProjectidOwnername(guideline, project, owner)
                .orElseThrow(GuidelineNotFoundException::new);

        return GuidelineDto.from(guidelineEntity);
    }

    /**
     * Returns all guidelines of the given project.
     *
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * 
     * @return {@link List} of {@link GuidelineDto} of the guidelines
     */
    public List<GuidelineDto> getGuidelineDtosOfProject(final String authenticationToken, final String owner,
            final String project) {
        final User requester = commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = commonService.getProject(owner, project);

        validationService.projectAccess(requester, projectEntity);

        final Query query = new GuidelineQueryBuilder().withOwner(owner).withProject(project).build();

        return this.guidelineRepository.findByQuery(query).stream().map(GuidelineDto::from)
                .collect(Collectors.toList());
    }

    // Setter

    /**
     * Creates a new guideline for the given project. Only the owner of the project and administrators can create
     * guidelines.
     * 
     * @param authenticationToken the authentication token of the user
     * @param owner               the owner of the project
     * @param project             the name of the project
     * @param file                the guideline file
     */
    @Transactional
    public void create(final String authenticationToken, final String owner, final String project,
            final MultipartFile file) {
    
        final User requester = commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = commonService.getProject(owner, project);

        validationService.projectAdminAccess(requester, projectEntity);
        validateUniqueGuideline(owner, project, file.getOriginalFilename());

        final String content;

        try {
            content = new String(file.getBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new GuidelineParseException("The file could not be read.");
        }

        final String name;
        try {
            name = file.getOriginalFilename().replace(".md", "");
        } catch (NullPointerException e) {
            throw new GuidelineParseException("Could not parse the guideline file name.");
        }

        Guideline guideline;
        try {
            guideline = new Guideline(name, projectEntity, content);
        } catch (Exception e) {
            throw new GuidelineParseException("The guideline could not be parsed. Check that the name of the guideline only contains alphanumerics and -.");
        }
        this.guidelineRepository.save(guideline);

    }

    private void validateUniqueGuideline(String owner, String project, String originalFilename) {
        if (this.guidelineRepository
                .findByIdNameAndIdProjectidNameAndIdProjectidOwnername(originalFilename, project, owner).isPresent()) {
            throw new GuidelineParseException("The guideline already exists.");
        }
    }

}
