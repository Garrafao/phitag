package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgement;
import de.garrafao.phitag.domain.judgement.wssimjudgement.WSSIMJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.wssimjudgement.query.WSSIMJudgementQueryJpa;

@Repository
public class WSSIMJudgementRepositoryBridge implements WSSIMJudgementRepository {

    private final WSSIMJudgementRepositoryJpa judgementRepository;

    @Autowired
    public WSSIMJudgementRepositoryBridge(WSSIMJudgementRepositoryJpa wssimJudgementRepositoryJpa) {
        this.judgementRepository = wssimJudgementRepositoryJpa;
    }

    @Override
    public List<WSSIMJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new WSSIMJudgementQueryJpa(query));
    }

    @Override
    public Page<WSSIMJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new WSSIMJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public WSSIMJudgement save(WSSIMJudgement judgement) {
        return this.judgementRepository.save(judgement);
    }

    @Override
    public void delete(WSSIMJudgement judgement) {
        this.judgementRepository.delete(judgement);
    }

}
