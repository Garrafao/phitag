package de.garrafao.phitag.computationalannotator.common.function;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.usepairinstance.UsePairInstanceApplicationService;
import de.garrafao.phitag.application.judgement.usepairjudgement.UsePairJudgementApplicationService;
import de.garrafao.phitag.computationalannotator.common.command.UsePairTutorialData;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import de.garrafao.phitag.domain.phase.Phase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTutorialData {


    private final CommonService commonService;
    private final UsePairJudgementApplicationService usePairJudgementApplicationService;


    public GetTutorialData(CommonService commonService, UsePairJudgementApplicationService usePairJudgementApplicationService, UsePairInstanceApplicationService usePairInstanceApplicationService) {
        this.commonService = commonService;
        this.usePairJudgementApplicationService = usePairJudgementApplicationService;
    }


    public List<UsePairTutorialData> getTutorial (final String owner, final  String project, final String phase) {
        final Phase phaseEntity = this.commonService.getPhase(owner, project, phase);

        List<UsePairTutorialData> tutorialData = new ArrayList<>();

        final List<UsePairJudgement> usePairJudgements =this.usePairJudgementApplicationService.findByPhase(phaseEntity);

        for(UsePairJudgement judgements : usePairJudgements){

            String firstUsage = judgements.getInstance().getFirstusage().getContext();
            String secondUsage =judgements.getInstance().getSecondusage().getContext();
            String lemma =  judgements.getInstance().getFirstusage().getLemma();
            String  judgement = judgements.getLabel();

            UsePairTutorialData tutorial = new UsePairTutorialData(firstUsage, secondUsage, lemma, judgement);
            tutorialData.add(tutorial);
        }
        return tutorialData;
    }
}
