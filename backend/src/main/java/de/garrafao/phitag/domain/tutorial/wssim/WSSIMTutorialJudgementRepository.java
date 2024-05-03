package de.garrafao.phitag.domain.tutorial.wssim;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WSSIMTutorialJudgementRepository {

    List<WSSIMTutorialJudgement> findByQuery(final Query query);

    Page<WSSIMTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);
    
    WSSIMTutorialJudgement save(WSSIMTutorialJudgement judgement);

}
