package de.garrafao.phitag.application.judgement;

import de.garrafao.phitag.application.annotationtype.data.AnnotationTypeEnum;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.entitlement.data.EntitlementEnum;
import de.garrafao.phitag.application.judgement.data.*;
import de.garrafao.phitag.application.judgement.lexsubjudgement.LexSubJudgementApplicationService;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.AddLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.DeleteLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.EditLexSubJudgementCommand;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.LexJudgementDto;
import de.garrafao.phitag.application.judgement.lexsubjudgement.data.tutorial.LexSubTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.sentimentandchoice.SentimentJudgementApplicationService;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.AddSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.DeleteSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.EditSentimentAndChoiceJudgementCommand;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.SentimentAndChoiceJudgementDto;
import de.garrafao.phitag.application.judgement.sentimentandchoice.data.tutorial.SentimentAndChoiceTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.usepairjudgement.UsePairJudgementApplicationService;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.AddUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.DeleteUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.EditUsePairJudgementCommand;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.UsePairJudgementDto;
import de.garrafao.phitag.application.judgement.usepairjudgement.data.tutorial.UsePairTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.userankjudgement.UseRankJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankjudgement.data.AddUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.DeleteUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.EditUseRankJudgementCommand;
import de.garrafao.phitag.application.judgement.userankjudgement.data.UseRankJudgementDto;
import de.garrafao.phitag.application.judgement.userankjudgement.data.tutorial.UseRankTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.userankpairjudgement.UseRankPairJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.AddUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.DeleteUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.EditUseRankPairJudgementCommand;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.UseRankPairJudgementDto;
import de.garrafao.phitag.application.judgement.userankpairjudgement.data.tutorial.UseRankPairTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.UseRankRelativeJudgementApplicationService;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.AddUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.DeleteUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.EditUseRankRelativeJudgementCommand;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.UseRankRelativeJudgementDto;
import de.garrafao.phitag.application.judgement.userankrelativejudgement.data.tutorial.UseRankRelativeTutorialJudgementDto;
import de.garrafao.phitag.application.judgement.wssimjudgement.WSSIMJudgementApplicationService;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.AddWSSIMJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.DeleteWSSIMJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.EditWSSIMJudgementCommand;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.WSSIMJudgementDto;
import de.garrafao.phitag.application.judgement.wssimjudgement.data.tutorial.WSSIMTutorialJudgementDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.annotationprocessinformation.AnnotationProcessInformation;
import de.garrafao.phitag.domain.annotationtype.error.AnnotationTypeNotFoundException;
import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.authentication.error.AccessDenidedException;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgement;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgement;
import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgement;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgement;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.tutorial.lexsub.LexSubTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.usepair.UsePairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgement;
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
public class JudgementApplicationService {

    // Repository

    // Services

    private final CommonService commonService;

    private final ValidationService validationService;

    private final UsePairJudgementApplicationService usePairJudgementApplicationService;

    private final UseRankJudgementApplicationService useRankJudgementApplicationService;
    private final UseRankRelativeJudgementApplicationService useRankRelativeJudgementApplicationService;

    private final UseRankPairJudgementApplicationService useRankPairJudgementApplicationService;



    private final WSSIMJudgementApplicationService wssimJudgementApplicationService;

    private final LexSubJudgementApplicationService lexSubJudgementApplicationService;

    private final SentimentJudgementApplicationService sentimentJudgementApplicationService;

    // Other

    @Autowired
    public JudgementApplicationService(
            final CommonService commonService,
            final ValidationService validationService,

            final UsePairJudgementApplicationService usePairJudgementApplicationService,
            final UseRankJudgementApplicationService useRankJudgementApplicationService,
            final UseRankRelativeJudgementApplicationService useRankRelativeJudgementApplicationService,
            final UseRankPairJudgementApplicationService useRankPairJudgementApplicationService,
            final WSSIMJudgementApplicationService wssimJudgementApplicationService,
            final LexSubJudgementApplicationService lexSubJudgementApplicationService,
            final SentimentJudgementApplicationService sentimentJudgementApplicationService) {
        this.commonService = commonService;
        this.validationService = validationService;

        this.usePairJudgementApplicationService = usePairJudgementApplicationService;
        this.useRankJudgementApplicationService = useRankJudgementApplicationService;
        this.useRankRelativeJudgementApplicationService = useRankRelativeJudgementApplicationService;
        this.useRankPairJudgementApplicationService = useRankPairJudgementApplicationService;
        this.wssimJudgementApplicationService = wssimJudgementApplicationService;
        this.lexSubJudgementApplicationService = lexSubJudgementApplicationService;
        this.sentimentJudgementApplicationService = sentimentJudgementApplicationService;
    }

    // Getters

    /**
     * Get all judgements for a given phase.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a list of all {@IJudgementDto} for the given phase
     */
    public List<IJudgementDto> getJudgementDto(final String authenticationToken, final String owner,
            final String project, final String phase) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        // IF requester is only annotator, only return judgments of the requester
        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), requester.getUsername());
        if (annotator.getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_USER.name())) {
            return this.getHistory(authenticationToken, owner, project, phase);
        }

        List<IJudgementDto> judgementDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    usePairJudgement -> judgementDtos.add(UsePairJudgementDto.from(usePairJudgement)));
        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    useRankJudgement -> judgementDtos.add(UseRankJudgementDto.from(useRankJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    useRankRelativeJudgement -> judgementDtos.add(UseRankRelativeJudgementDto.from(useRankRelativeJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    useRankPairJudgement -> judgementDtos.add(UseRankPairJudgementDto.from(useRankPairJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    wssimJudgement -> judgementDtos.add(WSSIMJudgementDto.from(wssimJudgement)));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    lexSubJudgement -> judgementDtos.add(LexJudgementDto.from(lexSubJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentJudgementApplicationService.findByPhase(phaseEntity).forEach(
                    sentimentJudgement -> judgementDtos.add(SentimentAndChoiceJudgementDto.from(sentimentJudgement)));
        }

        return judgementDtos;
    }

    /**
     * Get all judgments for a given annotator as a page.
     * 
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * 
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public PagedJudgementDto getPagedJudgementDto(
            final String authenticationToken,
            final String owner,
            final String project,
            final String phase,
            final int page,
            final int size,
            final String sort) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        // IF requester is only annotator, only return judgments of the requester
        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), requester.getUsername());
        if (annotator.getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_USER.name())) {
            return this.getPagedHistory(authenticationToken, owner, project, phase, page, size, sort);
        }

        final PagedJudgementDto pagedJudgementDto;

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            Page<UsePairJudgement> usePairJudgements = this.usePairJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    usePairJudgements.getContent().stream().map(UsePairJudgementDto::from).collect(Collectors.toList()),
                    usePairJudgements.getNumber(),
                    usePairJudgements.getSize(),
                    usePairJudgements.getTotalElements(),
                    usePairJudgements.getTotalPages());

        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            Page<UseRankJudgement> useRankJudgements = this.useRankJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankJudgements.getContent().stream().map(UseRankJudgementDto::from).collect(Collectors.toList()),
                    useRankJudgements.getNumber(),
                    useRankJudgements.getSize(),
                    useRankJudgements.getTotalElements(),
                    useRankJudgements.getTotalPages());

        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            Page<UseRankRelativeJudgement> useRankRelativeJudgements = this.useRankRelativeJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankRelativeJudgements.getContent().stream().map(UseRankRelativeJudgementDto::from).collect(Collectors.toList()),
                    useRankRelativeJudgements.getNumber(),
                    useRankRelativeJudgements.getSize(),
                    useRankRelativeJudgements.getTotalElements(),
                    useRankRelativeJudgements.getTotalPages());

        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            Page<UseRankPairJudgement> useRankPairJudgements = this.useRankPairJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankPairJudgements.getContent().stream().map(UseRankPairJudgementDto::from).collect(Collectors.toList()),
                    useRankPairJudgements.getNumber(),
                    useRankPairJudgements.getSize(),
                    useRankPairJudgements.getTotalElements(),
                    useRankPairJudgements.getTotalPages());

        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            Page<WSSIMJudgement> wssimJudgements = this.wssimJudgementApplicationService.findByPhase(phaseEntity, size,
                    page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    wssimJudgements.getContent().stream().map(WSSIMJudgementDto::from).collect(Collectors.toList()),
                    wssimJudgements.getNumber(),
                    wssimJudgements.getSize(),
                    wssimJudgements.getTotalElements(),
                    wssimJudgements.getTotalPages());
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            Page<LexSubJudgement> lexSubJudgements = this.lexSubJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    lexSubJudgements.getContent().stream().map(LexJudgementDto::from).collect(Collectors.toList()),
                    lexSubJudgements.getNumber(),
                    lexSubJudgements.getSize(),
                    lexSubJudgements.getTotalElements(),
                    lexSubJudgements.getTotalPages());
        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            Page<SentimentAndChoiceJudgement> sentimentJudgements = this.sentimentJudgementApplicationService.findByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    sentimentJudgements.getContent().stream().map(SentimentAndChoiceJudgementDto::from).collect(Collectors.toList()),
                    sentimentJudgements.getNumber(),
                    sentimentJudgements.getSize(),
                    sentimentJudgements.getTotalElements(),
                    sentimentJudgements.getTotalPages());
        }
        else {
            pagedJudgementDto = null;
        }

        return pagedJudgementDto;
    }

    /**
     * Get all judgements for a given annotator.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a list of all {@IJudgementDto} for the given annotator
     */
    public List<IJudgementDto> getHistory(final String authenticationToken, final String owner,
            final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        List<IJudgementDto> judgementDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(usePairJudgement -> judgementDtos.add(UsePairJudgementDto.from(usePairJudgement)));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(useRankJudgement -> judgementDtos.add(UseRankJudgementDto.from(useRankJudgement)));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(useRankRelativeJudgement -> judgementDtos.add(UseRankRelativeJudgementDto.from(useRankRelativeJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(useRankPairJudgement -> judgementDtos.add(UseRankPairJudgementDto.from(useRankPairJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(wssimJudgement -> judgementDtos.add(WSSIMJudgementDto.from(wssimJudgement)));
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(lexSubJudgement -> judgementDtos.add(LexJudgementDto.from(lexSubJudgement)));
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentJudgementApplicationService.getHistory(phaseEntity, annotator)
                    .forEach(sentimentJudgement -> judgementDtos.add(SentimentAndChoiceJudgementDto.from(sentimentJudgement)));
        }

        return judgementDtos;
    }

    /**
     * Get all judgements for a given annotator as a page.
     * 
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public PagedJudgementDto getPagedHistory(
            final String authenticationToken,
            final String owner,
            final String project,
            final String phase,
            final int page,
            final int size,
            final String sort) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        final PagedJudgementDto pagedJudgementDto;

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            Page<UsePairJudgement> usePairJudgements = this.usePairJudgementApplicationService.getHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    usePairJudgements.getContent().stream().map(UsePairJudgementDto::from).collect(Collectors.toList()),
                    usePairJudgements.getNumber(),
                    usePairJudgements.getSize(),
                    usePairJudgements.getTotalElements(),
                    usePairJudgements.getTotalPages());

        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            Page<UseRankJudgement> useRankJudgements = this.useRankJudgementApplicationService.getHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankJudgements.getContent().stream().map(UseRankJudgementDto::from).collect(Collectors.toList()),
                    useRankJudgements.getNumber(),
                    useRankJudgements.getSize(),
                    useRankJudgements.getTotalElements(),
                    useRankJudgements.getTotalPages());

        }  else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            Page<UseRankRelativeJudgement> useRankRelativeJudgements = this.useRankRelativeJudgementApplicationService.getHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankRelativeJudgements.getContent().stream().map(UseRankRelativeJudgementDto::from).collect(Collectors.toList()),
                    useRankRelativeJudgements.getNumber(),
                    useRankRelativeJudgements.getSize(),
                    useRankRelativeJudgements.getTotalElements(),
                    useRankRelativeJudgements.getTotalPages());

        }

        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            Page<UseRankPairJudgement> useRankPairJudgements = this.useRankPairJudgementApplicationService.getHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    useRankPairJudgements.getContent().stream().map(UseRankPairJudgementDto::from).collect(Collectors.toList()),
                    useRankPairJudgements.getNumber(),
                    useRankPairJudgements.getSize(),
                    useRankPairJudgements.getTotalElements(),
                    useRankPairJudgements.getTotalPages());

        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            Page<WSSIMJudgement> wssimJudgements = this.wssimJudgementApplicationService.getHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    wssimJudgements.getContent().stream().map(WSSIMJudgementDto::from).collect(Collectors.toList()),
                    wssimJudgements.getNumber(),
                    wssimJudgements.getSize(),
                    wssimJudgements.getTotalElements(),
                    wssimJudgements.getTotalPages());
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            Page<LexSubJudgement> lexSubJudgements = this.lexSubJudgementApplicationService.getHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    lexSubJudgements.getContent().stream().map(LexJudgementDto::from).collect(Collectors.toList()),
                    lexSubJudgements.getNumber(),
                    lexSubJudgements.getSize(),
                    lexSubJudgements.getTotalElements(),
                    lexSubJudgements.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            Page<SentimentAndChoiceJudgement> sentimentJudgements = this.sentimentJudgementApplicationService.getHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    sentimentJudgements.getContent().stream().map(SentimentAndChoiceJudgementDto::from).collect(Collectors.toList()),
                    sentimentJudgements.getNumber(),
                    sentimentJudgements.getSize(),
                    sentimentJudgements.getTotalElements(),
                    sentimentJudgements.getTotalPages());
        }
        else {
            pagedJudgementDto = null;
        }

        return pagedJudgementDto;
    }

    /**
     * Export all judgements for a given phase.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a {@link InputStreamResource} CSV file containing all judgements for
     *         the given phase
     */
    public InputStreamResource exportJudgement(final String authenticationToken, final String owner,
            final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminOrBot(requester, phaseEntity.getProject());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            return this.usePairJudgementApplicationService.exportUsePairJudgement(phaseEntity);
        } else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            return this.useRankJudgementApplicationService.exportUseRankJudgement(phaseEntity);
        }
        else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            return this.useRankRelativeJudgementApplicationService.exportUseRankRelativeJudgement(phaseEntity);
        }
        else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            return this.useRankPairJudgementApplicationService.exportUseRankPairJudgement(phaseEntity);
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            return this.wssimJudgementApplicationService.exportWSSIMJudgement(phaseEntity);
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            return this.lexSubJudgementApplicationService.exportJudgement(phaseEntity);
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            return this.sentimentJudgementApplicationService.exportJudgement(phaseEntity);
        }

        throw new AnnotationTypeNotFoundException();
    }

    /**
     * Count all judgements for a given annotator.
     * 
     * @param annotator the annotator
     * @return the number of judgements
     */
    public int countJudgements(Annotator annotator) {
        return this.usePairJudgementApplicationService.countJudgements(annotator)
                + this.wssimJudgementApplicationService.countJudgements(annotator);
    }

    /**
     * Count all judgements for a given phase and annotator.
     * 
     * @param annotator the annotator
     * @param phase     the phase
     * @return the number of judgements
     */
    public int countJudgements(Annotator annotator, Phase phase) {
        return this.usePairJudgementApplicationService.countJudgements(annotator, phase)
                + this.wssimJudgementApplicationService.countJudgements(annotator, phase);
    }


    // Setters

    /**
     * Add judgement for a given phase from a CSV file.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @param file                the CSV file containing the judgements
     */
    @Transactional
    public void addJudgements(final String authenticationToken, final String owner,
            final String project, final String phase, final MultipartFile file) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminAccess(requester, phaseEntity.getProject());

        final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        }  else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentJudgementApplicationService.save(phaseEntity, annotator, file);
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

    /**
     * Edit a judgement.
     * 
     * @param authenticationToken
     * @param command
     */
    @Transactional
    public void edit(final String authenticationToken, final IEditJudgementCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        final Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(),
                requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.edit(phaseEntity, annotator, (EditUsePairJudgementCommand) command);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.edit(phaseEntity, annotator, (EditUseRankJudgementCommand) command);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.edit(phaseEntity, annotator, (EditUseRankRelativeJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.edit(phaseEntity, annotator, (EditUseRankPairJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.edit(phaseEntity, annotator, (EditWSSIMJudgementCommand) command);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.edit(phaseEntity, annotator, (EditLexSubJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentJudgementApplicationService.edit(phaseEntity, annotator, (EditSentimentAndChoiceJudgementCommand) command);
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

    /**
     * Delete a judgement.
     * 
     * @param authenticationToken
     * @param command
     */
    @Transactional
    public void delete(final String authenticationToken, final IDeleteJudgementCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        final Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(),
                requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteUsePairJudgementCommand) command);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            this.useRankJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteUseRankJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            this.useRankRelativeJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteUseRankRelativeJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            this.useRankPairJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteUseRankPairJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            this.wssimJudgementApplicationService.delete(phaseEntity, annotator, (DeleteWSSIMJudgementCommand) command);
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            this.lexSubJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteLexSubJudgementCommand) command);
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            this.sentimentJudgementApplicationService.delete(phaseEntity, annotator,
                    (DeleteSentimentAndChoiceJudgementCommand) command);
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

    /**
     * Add judgement for a given phase.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param command             the command containing the judgement
     */
    @Transactional
    public void annotate(final String authenticationToken, final IAddJudgementCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(command.getOwner(), command.getProject(),
                command.getPhase());

        this.validationService.phaseAnnotationAccess(requester, phaseEntity);

        if (phaseEntity.isTutorial()) {
            throw new AccessDenidedException(
                    "Tutorial phases are not single-annotatable. Use the bulk-annotation instead.");
        }

        final Annotator annotator = this.commonService.getAnnotator(command.getOwner(), command.getProject(),
                requester.getUsername());
        final AnnotationProcessInformation annotationProcessInformation = this.commonService
                .getAnnotationProcessInformation(annotator, phaseEntity);

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            try {
                this.usePairJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddUsePairJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            try {
                this.useRankJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddUseRankJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            try {
                this.useRankRelativeJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddUseRankRelativeJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            try {
                this.useRankPairJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddUseRankPairJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            try {
                this.wssimJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddWSSIMJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);

            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            try {
                this.lexSubJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddLexSubJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);

            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            try {
                this.sentimentJudgementApplicationService.annotate(phaseEntity, annotator,
                        (AddSentimentAndChoiceJudgementCommand) command);
                annotationProcessInformation.setIndex(annotationProcessInformation.getIndex() + 1);

            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }

    /**
     * Add bulk judgement for a given phase.
     * If the phase is a tutorial phase, the judgements are checked against the
     * tutorial judgements and if they are correct, the tutorial phase is marked as
     * completed.
     * 
     * @param authenticationToken the authentication token of the requesting user
     * @param commands            list of commands containing the judgement
     */
    @Transactional
    public void annotateBulk(final String authenticationToken, final List<IAddJudgementCommand> commands) {
        if (commands.isEmpty()) {
            return;
        }

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(commands.get(0).getOwner(), commands.get(0).getProject(),
                commands.get(0).getPhase());

        this.validationService.phaseAnnotationAccess(requester, phaseEntity);

        final Annotator annotator = this.commonService.getAnnotator(commands.get(0).getOwner(),
                commands.get(0).getProject(), requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            try {
                this.usePairJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddUsePairJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            try {
                this.useRankJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddUseRankJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            try {
                this.useRankRelativeJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddUseRankRelativeJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            try {
                this.useRankPairJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddUseRankPairJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }

        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            try {
                this.wssimJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddWSSIMJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            try {
                this.lexSubJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddLexSubJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            try {
                this.sentimentJudgementApplicationService.annotateBulk(phaseEntity, annotator,
                        commands.stream().map(AddSentimentAndChoiceJudgementCommand.class::cast).collect(Collectors.toList()));
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return;
        }

        throw new AnnotationTypeNotFoundException();
    }
    public PagedJudgementDto getPagedJudgementDtoByAnnotator(
            final String authenticationToken,
            final String owner,
            final String project,
            final String phase,
            final int page,
            final int size,
            final String sort) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        // IF requester is only annotator, only return judgments of the requester
        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), requester.getUsername());
        if (annotator.getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_USER.name())) {
            return this.getPagedHistory(authenticationToken, owner, project, phase, page, size, sort);
        }else {
            return null;

        }

    }


    /**
     * count all attempted judgement by annotator  for a given phase.
     * If the phase is a tutorial phase, the judgements are checked against the
     * tutorial judgements and if they are correct, the tutorial phase is marked as
     * completed.
     *
     * @param authenticationToken the authentication token of the requesting user
     */
    public int  countAttemptedJudgements(final String authenticationToken,
                                         final String owner,
                                         final String project, final String phase) {


        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);
        this.commonService.isUserAnnotator(owner, project, requester.getUsername());
        this.validationService.phaseAnnotationAccess(requester, phaseEntity);

        int count;

        final Annotator annotator = this.commonService.getAnnotator(owner,
                project, requester.getUsername());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            try {
              count = this.usePairJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            try {
               count = this.useRankJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            try {
               count =  this.useRankRelativeJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            try {
                count = this.useRankPairJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        }

        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            try {
                count = this.wssimJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            try {
               count = this.lexSubJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            try {
                count = this.sentimentJudgementApplicationService.countAttemptedJudgements(phaseEntity, annotator);
            } catch (ClassCastException e) {
                throw new AnnotationTypeNotFoundException();
            }
            return count;
        }
        throw new AnnotationTypeNotFoundException();
    }

    //Tutorial


    /**
     * Get all  tutorial judgements for a given phase.
     *
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a list of all {@IJudgementDto} for the given phase
     */
    public List<IJudgementDto> getTutorialJudgementDto(final String authenticationToken, final String owner,
                                               final String project, final String phase) {

        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        // IF requester is only annotator, only return judgments of the requester
        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), requester.getUsername());
        if (annotator.getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_USER.name())) {
            return this.getHistory(authenticationToken, owner, project, phase);
        }

        List<IJudgementDto> judgementDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.findTutorialByPhase(phaseEntity).forEach(
                    usePairTutorialJudgement -> judgementDtos.add(UsePairTutorialJudgementDto.from(usePairTutorialJudgement)));
        }

        return judgementDtos;
    }

    /**
     * Get all tutorial judgments for a given annotator as a page.
     *
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     *
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public PagedJudgementDto getTutorialPagedJudgementDto(
            final String authenticationToken,
            final String owner,
            final String project,
            final String phase,
            final int page,
            final int size,
            final String sort) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);

        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAccess(requester, phaseEntity.getProject());
        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        // IF requester is only annotator, only return judgments of the requester
        Annotator annotator = this.commonService.getAnnotator(phaseEntity.getId().getProjectid().getOwnername(),
                phaseEntity.getId().getProjectid().getName(), requester.getUsername());
        if (annotator.getEntitlement().getName().equals(EntitlementEnum.ENTITLEMENT_USER.name())) {
            return this.getPagedHistory(authenticationToken, owner, project, phase, page, size, sort);
        }

        final PagedJudgementDto pagedJudgementDto;

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            Page<UsePairTutorialJudgement> usePairTutorialJudgements = this.usePairJudgementApplicationService.findTutorialByPhase(phaseEntity,
                    size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    usePairTutorialJudgements.getContent().stream().map(UsePairTutorialJudgementDto::from).collect(Collectors.toList()),
                    usePairTutorialJudgements.getNumber(),
                    usePairTutorialJudgements.getSize(),
                    usePairTutorialJudgements.getTotalElements(),
                    usePairTutorialJudgements.getTotalPages());

        }  else {
            pagedJudgementDto = null;
        }

        return pagedJudgementDto;
    }


    /**
     * Get all judgements for a given annotator.
     *
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a list of all {@IJudgementDto} for the given annotator
     */
    public List<IJudgementDto> getTutorialHistory(final String authenticationToken, final String owner,
                                          final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        List<IJudgementDto> judgementDtos = new ArrayList<>();

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            this.usePairJudgementApplicationService.getTutorialHistory(phaseEntity, annotator)
                    .forEach(usePairTutorialJudgement -> judgementDtos.add(UsePairTutorialJudgementDto.from(usePairTutorialJudgement)));
        }

        return judgementDtos;
    }




    /**
     * Get all judgements for a given annotator as a page.
     *
     * @param authenticationToken
     * @param owner
     * @param project
     * @param phase
     * @param page
     * @param size
     * @param sort
     * @return
     */
    public PagedJudgementDto getPagedTutorialHistory(
            final String authenticationToken,
            final String owner,
            final String project,
            final String phase,
            final int page,
            final int size,
            final String sort) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAnnotatorAccess(requester, phaseEntity.getProject());

        if (phaseEntity.isTutorial()) {
            this.validationService.projectAdminAccess(requester, phaseEntity.getProject());
        }

        final Annotator annotator = this.commonService.getAnnotator(owner, project, requester.getUsername());

        final PagedJudgementDto pagedJudgementDto;

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            Page<UsePairTutorialJudgement> usePairTutorialJudgements = this.usePairJudgementApplicationService.getTutorialHistory(phaseEntity,
                    annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    usePairTutorialJudgements.getContent().stream().map(UsePairTutorialJudgementDto::from).collect(Collectors.toList()),
                    usePairTutorialJudgements.getNumber(),
                    usePairTutorialJudgements.getSize(),
                    usePairTutorialJudgements.getTotalElements(),
                    usePairTutorialJudgements.getTotalPages());

        }  else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            Page<LexSubTutorialJudgement> lexSubJudgements = this.lexSubJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    lexSubJudgements.getContent().stream().map(LexSubTutorialJudgementDto::from).collect(Collectors.toList()),
                    lexSubJudgements.getNumber(),
                    lexSubJudgements.getSize(),
                    lexSubJudgements.getTotalElements(),
                    lexSubJudgements.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            Page<SentimentAndChoiceTutorialJudgement> judgements = this.sentimentJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    judgements.getContent().stream().map(SentimentAndChoiceTutorialJudgementDto::from).collect(Collectors.toList()),
                    judgements.getNumber(),
                    judgements.getSize(),
                    judgements.getTotalElements(),
                    judgements.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            Page<UseRankTutorialJudgement> Judgements = this.useRankJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    Judgements.getContent().stream().map(UseRankTutorialJudgementDto::from).collect(Collectors.toList()),
                    Judgements.getNumber(),
                    Judgements.getSize(),
                    Judgements.getTotalElements(),
                    Judgements.getTotalPages());
        }

        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            Page<UseRankRelativeTutorialJudgement> Judgements = this.useRankRelativeJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    Judgements.getContent().stream().map(UseRankRelativeTutorialJudgementDto::from).collect(Collectors.toList()),
                    Judgements.getNumber(),
                    Judgements.getSize(),
                    Judgements.getTotalElements(),
                    Judgements.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            Page<UseRankPairTutorialJudgement> Judgements = this.useRankPairJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    Judgements.getContent().stream().map(UseRankPairTutorialJudgementDto::from).collect(Collectors.toList()),
                    Judgements.getNumber(),
                    Judgements.getSize(),
                    Judgements.getTotalElements(),
                    Judgements.getTotalPages());
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            Page<WSSIMTutorialJudgement> judgements = this.wssimJudgementApplicationService.getTutorialHistory(
                    phaseEntity, annotator, size, page, sort);

            pagedJudgementDto = new PagedJudgementDto(
                    judgements.getContent().stream().map(WSSIMTutorialJudgementDto::from).collect(Collectors.toList()),
                    judgements.getNumber(),
                    judgements.getSize(),
                    judgements.getTotalElements(),
                    judgements.getTotalPages());
        }

        else {
            pagedJudgementDto = null;
        }

        return pagedJudgementDto;
    }

    /**
     * Export all  tutorial judgements for a given phase.
     *
     * @param authenticationToken the authentication token of the requesting user
     * @param owner               the owner of the project
     * @param project             the project
     * @param phase               the phase
     * @return a {@link InputStreamResource} CSV file containing all judgements for
     *         the given phase
     */
    public InputStreamResource exportTutorialJudgement(final String authenticationToken, final String owner,
                                               final String project, final String phase) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        this.validationService.projectAdminOrBot(requester, phaseEntity.getProject());

        if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USEPAIR.name())) {
            return this.usePairJudgementApplicationService.exportUsePairTutorialJudgement(phaseEntity);
        } else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK.name())) {
            return this.useRankJudgementApplicationService.exportUseRankTutorialJudgement(phaseEntity);
        }
        else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_RELATIVE.name())) {
            return this.useRankRelativeJudgementApplicationService.exportUseRankRelativeTutorialJudgement(phaseEntity);
        }
        else  if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_USERANK_PAIR.name())) {
            return this.useRankPairJudgementApplicationService.exportUseRankPairTutorialJudgement(phaseEntity);
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_WSSIM.name())) {
            return this.wssimJudgementApplicationService.exportWSSIMTutorialJudgement(phaseEntity);
        } else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_LEXSUB.name())) {
            return this.lexSubJudgementApplicationService.exportTutorialJudgement(phaseEntity);
        }
        else if (phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_SENTIMENT.name()) ||
                phaseEntity.getAnnotationType().getName().equals(AnnotationTypeEnum.ANNOTATIONTYPE_CHOICE.name())) {
            return this.sentimentJudgementApplicationService.exportTutorialJudgement(phaseEntity);
        }
        throw new AnnotationTypeNotFoundException();
    }
}
