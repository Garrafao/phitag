package de.garrafao.phitag.application.instance;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.data.DeleteInstanceCommand;
import de.garrafao.phitag.application.instance.data.IInstanceDto;
import de.garrafao.phitag.application.instance.data.PagedInstanceDto;
import de.garrafao.phitag.application.instance.lexsubinstance.LexSubInstanceApplicationService;
import de.garrafao.phitag.application.instance.lexsubinstance.data.LexSubInstanceDto;
import de.garrafao.phitag.application.instance.sentimentandchoice.SentimentAndChoiceInstanceApplicationService;
import de.garrafao.phitag.application.instance.sentimentandchoice.data.SentimentAndChoiceInstanceDto;
import de.garrafao.phitag.application.instance.spaninstance.SpanInstanceApplicationService;
import de.garrafao.phitag.application.instance.usepairinstance.UsePairInstanceApplicationService;
import de.garrafao.phitag.application.instance.usepairinstance.data.UsePairInstanceDto;
import de.garrafao.phitag.application.instance.userankinstance.UseRankInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankinstance.data.UseRankInstanceDto;
import de.garrafao.phitag.application.instance.userankpairinstance.UseRankPairInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankpairinstance.data.UseRankPairInstanceDto;
import de.garrafao.phitag.application.instance.userankrelative.UseRankRelativeInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankrelative.data.UseRankRelativeInstanceDto;
import de.garrafao.phitag.application.instance.wssiminstance.WSSIMInstanceApplicationService;
import de.garrafao.phitag.application.instance.wssiminstance.data.WSSIMInstanceDto;
import de.garrafao.phitag.application.instance.wssimtag.WSSIMTagApplicationService;
import de.garrafao.phitag.application.instance.wssimtag.data.WSSIMTagDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotationtype.error.AnnotationTypeNotFoundException;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.lexsub.LexSubInstance;
import de.garrafao.phitag.domain.instance.sentimentandchoice.SentimentAndChoiceInstance;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.instance.userankpairinstances.UseRankPairInstance;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.instance.wssiminstance.WSSIMInstance;
import de.garrafao.phitag.domain.instance.wssimtag.WSSIMTag;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstanceApplicationService {

    // Repository

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    private final UsePairInstanceApplicationService usePairInstanceApplicationService;

    private final UseRankInstanceApplicationService useRankInstanceApplicationService;

    private final UseRankRelativeInstanceApplicationService useRankRelativeInstanceApplicationService;

    private final UseRankPairInstanceApplicationService useRankPairInstanceApplicationService;


    private final WSSIMInstanceApplicationService wssimInstanceApplicationService;

    private final WSSIMTagApplicationService wssimTagApplicationService;

    private final LexSubInstanceApplicationService lexSubInstanceApplicationService;

    private final SentimentAndChoiceInstanceApplicationService sentimentAndChoiceInstanceApplicationService;

    private final SpanInstanceApplicationService spanInstanceApplicationService;

    // Other

    @Autowired
    public InstanceApplicationService(
            final CommonService commonService,
            final ValidationService validationService,

            final UsePairInstanceApplicationService usePairInstanceApplicationService,
            final UseRankInstanceApplicationService useRankInstanceApplicationService,
            final UseRankRelativeInstanceApplicationService useRankRelativeInstanceApplicationService,
            final UseRankPairInstanceApplicationService useRankPairInstanceApplicationService,
            final WSSIMInstanceApplicationService wssimInstanceApplicationService,
            final WSSIMTagApplicationService wssimTagApplicationService,
            final LexSubInstanceApplicationService lexSubInstanceApplicationService,
            final SentimentAndChoiceInstanceApplicationService sentimentAndChoiceInstanceApplicationService,
            final SpanInstanceApplicationService spanInstanceApplicationService) {
        this.commonService = commonService;
        this.validationService = validationService;

        this.usePairInstanceApplicationService = usePairInstanceApplicationService;
        this.useRankInstanceApplicationService = useRankInstanceApplicationService;
        this.useRankRelativeInstanceApplicationService = useRankRelativeInstanceApplicationService;
        this.useRankPairInstanceApplicationService = useRankPairInstanceApplicationService;
        this.wssimInstanceApplicationService = wssimInstanceApplicationService;
        this.wssimTagApplicationService = wssimTagApplicationService;
        this.lexSubInstanceApplicationService = lexSubInstanceApplicationService;
        this.sentimentAndChoiceInstanceApplicationService = sentimentAndChoiceInstanceApplicationService;
        this.spanInstanceApplicationService = spanInstanceApplicationService;
    }

    // Getters
    /**
     * Get all instances for a given phase.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @param additional          whether to send additional information (e.g. tags
     *                            for WSSIM)
     * @return a list of all {@IInstanceDto} for the given phase
     */
    public List<IInstanceDto> getInstanceDtos(final String authenticationToken, final String owner,
            final String project, final String phase, final boolean additional) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        List<IInstanceDto> instanceDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(usePairInstance -> instanceDtos.add(UsePairInstanceDto.from(usePairInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(useRankInstance -> instanceDtos.add(UseRankInstanceDto.from(useRankInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(useRankRelativeInstance -> instanceDtos.add(UseRankRelativeInstanceDto.from(useRankRelativeInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(useRankPairInstance -> instanceDtos.add(UseRankPairInstanceDto.from(useRankPairInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (additional) {
                this.wssimTagApplicationService.findByPhase(phaseEntity)
                        .forEach(wssimTag -> instanceDtos.add(WSSIMTagDto.from(wssimTag)));
            } else {
                this.wssimInstanceApplicationService.findByPhase(phaseEntity)
                        .forEach(wssimInstance -> instanceDtos.add(WSSIMInstanceDto.from(wssimInstance)));
            }
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(lexSubInstance -> instanceDtos.add(LexSubInstanceDto.from(lexSubInstance)));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())) {
            this.sentimentAndChoiceInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(sentimentInstance -> instanceDtos.add(SentimentAndChoiceInstanceDto.from(sentimentInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentAndChoiceInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(sentimentInstance -> instanceDtos.add(SentimentAndChoiceInstanceDto.from(sentimentInstance)));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            this.sentimentAndChoiceInstanceApplicationService.findByPhase(phaseEntity)
                    .forEach(sentimentInstance -> instanceDtos.add(SentimentAndChoiceInstanceDto.from(sentimentInstance)));
        }

        return instanceDtos;
    }


    /**
     * Get all instances for a given phase as page
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @param additional          whether to send additional information (e.g. tags
     *                            for WSSIM)
     * @return a page of all {@IInstanceDto} for the given phase
     */
    public PagedInstanceDto getPagedInstanceDto(
            final String authenticationToken,
            final String owner, final String project, final String phase, final boolean additional,
            final int page, final int size, final String order) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        final PagedInstanceDto pagedInstanceDto;

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            Page<UsePairInstance> pagedInstance = this.usePairInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);
            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(UsePairInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());

        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (additional) {
                Page<WSSIMTag> pagedInstance = this.wssimTagApplicationService.findByPhasePaged(phaseEntity, size, page,
                        order);

                pagedInstanceDto = new PagedInstanceDto(
                        pagedInstance.getContent().stream().map(WSSIMTagDto::from).collect(Collectors.toList()),
                        pagedInstance.getNumber(),
                        pagedInstance.getSize(),
                        pagedInstance.getTotalElements(),
                        pagedInstance.getTotalPages());
            } else {
                Page<WSSIMInstance> pagedInstance = this.wssimInstanceApplicationService.findByPhasePaged(phaseEntity,
                        size, page, order);

                pagedInstanceDto = new PagedInstanceDto(
                        pagedInstance.getContent().stream().map(WSSIMInstanceDto::from).collect(Collectors.toList()),
                        pagedInstance.getNumber(),
                        pagedInstance.getSize(),
                        pagedInstance.getTotalElements(),
                        pagedInstance.getTotalPages());
            }
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            Page<LexSubInstance> pagedInstance = this.lexSubInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);

            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(LexSubInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());
        }  else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            Page<UseRankInstance> pagedInstance = this.useRankInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);
            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(UseRankInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());

        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            Page<UseRankRelativeInstance> pagedInstance = this.useRankRelativeInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);
            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(UseRankRelativeInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            Page<UseRankPairInstance> pagedInstance = this.useRankPairInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);
            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(UseRankPairInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())
        || phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            Page<SentimentAndChoiceInstance> pagedInstance = this.sentimentAndChoiceInstanceApplicationService.findByPhasePaged(phaseEntity,
                    size, page, order);

            pagedInstanceDto = new PagedInstanceDto(
                    pagedInstance.getContent().stream().map(SentimentAndChoiceInstanceDto::from).collect(Collectors.toList()),
                    pagedInstance.getNumber(),
                    pagedInstance.getSize(),
                    pagedInstance.getTotalElements(),
                    pagedInstance.getTotalPages());
        }

        else {
            pagedInstanceDto = new PagedInstanceDto();
        }

        return pagedInstanceDto;

    }

    /**
     * Get a random instance for a given phase.
     * 
     * Note: This will be extended and probably replaced with a more sophisticated
     * way
     * to get instances. For now, this is just a placeholder. The current
     * implementation
     * just randomly picks an instance. Later, the instance will be chosen based on
     * sampling-strategies and
     * the user's history.
     * 
     * TODO: Stop annotator if sampling index is out of bounds
     * TODO: Recalculate sampling order if instances are added or removed (update
     * imm), or if an annotator is added (wait till actual annotation)
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a random {@IInstanceDto} for the given phase
     */
    public IInstanceDto getAnnotationInstance(final String authenticationToken, final String owner,
            final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        final Annotator annotator = this.commonService.getAnnotator(
                phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(),
                requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            return UsePairInstanceDto
                    .from(this.usePairInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            return UseRankInstanceDto
                    .from(this.useRankInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            return UseRankRelativeInstanceDto
                    .from(this.useRankRelativeInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            return UseRankPairInstanceDto
                    .from(this.useRankPairInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            return WSSIMInstanceDto
                    .from(this.wssimInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            return LexSubInstanceDto
                    .from(this.lexSubInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())
                || phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            return SentimentAndChoiceInstanceDto
                    .from(this.sentimentAndChoiceInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            return SentimentAndChoiceInstanceDto
                    .from(this.sentimentAndChoiceInstanceApplicationService.getAnnotationInstance(phaseEntity, annotator));
        }

        return null;
    }

    /**
     * Export all instances as a CSV file.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @param additional          whether to send additional information (e.g. tags
     *                            for WSSIM)
     * @return a {@link InputStreamResource} CSV file containing all instances
     */
    public InputStreamResource exportInstance(final String authenticationToken, final String owner,
            final String project, final String phase, final boolean additional) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(requester, phaseEntity.getProject());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            return this.usePairInstanceApplicationService.exportUsePairInstance(phaseEntity);
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            return this.useRankInstanceApplicationService.exportUseRankInstance(phaseEntity);
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            return this.useRankRelativeInstanceApplicationService.exportUseRankInstance(phaseEntity);
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            return this.useRankPairInstanceApplicationService.exportUseRankPairInstance(phaseEntity);
        }


        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (additional) {
                return this.wssimTagApplicationService.exportWSSIMTag(phaseEntity);
            }
            return this.wssimInstanceApplicationService.exportWSSIMInstance(phaseEntity);
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            return this.lexSubInstanceApplicationService.exportInstance(phaseEntity);
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())
        || phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            return this.sentimentAndChoiceInstanceApplicationService.exportInstance(phaseEntity);
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            return this.sentimentAndChoiceInstanceApplicationService.exportInstance(phaseEntity);
        }


        throw new AnnotationTypeNotFoundException();

    }

    // Setters

    /**
     * Import instances from a CSV file.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @param additional          whether to send additional information (e.g. tags
     *                            for WSSIM)
     * @param file                the CSV file
     */
    @Transactional
    public void addInstances(final String authenticationToken, final String owner,
            final String project, final String phase, final boolean additional, final MultipartFile file) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(requester, phaseEntity.getProject());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairInstanceApplicationService.save(phaseEntity, file);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankInstanceApplicationService.save(phaseEntity, file);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeInstanceApplicationService.save(phaseEntity, file);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairInstanceApplicationService.save(phaseEntity, file);
            return;
        }


        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (additional) {
                this.wssimTagApplicationService.save(phaseEntity, file);
                return;
            }
            this.wssimInstanceApplicationService.save(phaseEntity, file);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubInstanceApplicationService.save(phaseEntity, file);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentAndChoiceInstanceApplicationService.save(phaseEntity, file);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            this.sentimentAndChoiceInstanceApplicationService.save(phaseEntity, file);
            return;
        }
        throw new AnnotationTypeNotFoundException();

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

     *                            Additional Data (e.g. WSSIM -> sense)
     * @param file
     *                            The instancedata to add
     */
    @Transactional
    public void generateInstances(
            final String authenticationToken,
            final String owner, final String project, final String phase,
            final List<String> labels, final String nonLabel, 
            final MultipartFile file) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(requester, phaseEntity.getProject());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairInstanceApplicationService.generateInstances(phaseEntity, labels, nonLabel);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankInstanceApplicationService.generateInstances(phaseEntity, labels, nonLabel);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeInstanceApplicationService.generateInstances(phaseEntity, labels, nonLabel);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (file.isEmpty()) {
                throw new AnnotationTypeNotFoundException();
            }
            this.wssimTagApplicationService.save(phaseEntity, file);
            this.wssimInstanceApplicationService.generateInstances(phaseEntity, labels, nonLabel);
            return;
        }

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubInstanceApplicationService.generateInstances(phaseEntity);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())
        || phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentAndChoiceInstanceApplicationService.generateInstances(phaseEntity);
            return;
        }
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            this.sentimentAndChoiceInstanceApplicationService.generateInstances(phaseEntity);
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

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
    public List<WSSIMTagDto> getWssimTagsByLemma(String authenticationToken, String owner, String project,
            String phase, String lemma) {
        return this.wssimTagApplicationService
                .findByPhaseAndLemma(this.commonService.getPhase(owner, project, phase), lemma)
                .stream().map(WSSIMTagDto::from).collect(Collectors.toList());
    }

    // Getter

    public int  countALlocatedInstance(final String authenticationToken, final String owner,
                                              final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);
        final  Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        return this.commonService.countAllocatedInstance(annotator, phaseEntity);

    }

    /**
     * Delete a instance.
     *
     * @param authenticationToken
     * @param command
     */
    @Transactional
    public void delete(String authenticationToken, DeleteInstanceCommand command) {
        // Retrieve requester and phaseEntity based on authentication token and command
        User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(), command.getPhase());

        // Validate project annotator access
        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        // Retrieve annotator
        Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(), requester.getUsername());

        // Handle deletion based on annotation type
        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            // Ensure command is of type DeleteUsepairInstanceCommand before casting
            if (command instanceof DeleteInstanceCommand) {
                this.usePairInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Command is not of type DeleteUsepairInstanceCommand");
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.lexSubInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.useRankInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.useRankPairInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.useRankRelativeInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.wssimInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.sentimentAndChoiceInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.sentimentAndChoiceInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SPAN.name())) {
            if (command instanceof DeleteInstanceCommand) {
                this.sentimentAndChoiceInstanceApplicationService.delete(phaseEntity, annotator, command);
            } else {
                throw new IllegalArgumentException("Internal server error");
            }
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

}
