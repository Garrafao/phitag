package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.wssim;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.wssim.WSSIMTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.wssim.query.WSSIMTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WSSIMTutorialJudgementRepositoryBridge implements WSSIMTutorialJudgementRepository {

    private final WSSIMTutorialJudgementRepositoryJpa judgementRepository;

    @Autowired
    public WSSIMTutorialJudgementRepositoryBridge(WSSIMTutorialJudgementRepositoryJpa wssimTutorialJudgementRepositoryJpa) {
        this.judgementRepository = wssimTutorialJudgementRepositoryJpa;
    }

    @Override
    public List<WSSIMTutorialJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new WSSIMTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<WSSIMTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new WSSIMTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public WSSIMTutorialJudgement save(WSSIMTutorialJudgement judgement) {
        return this.judgementRepository.save(judgement);
    }

}
