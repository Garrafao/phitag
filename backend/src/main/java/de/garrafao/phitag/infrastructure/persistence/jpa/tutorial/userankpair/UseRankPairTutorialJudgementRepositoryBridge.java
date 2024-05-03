package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankpair;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankpair.UseRankPairTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankpair.query.UseRankPairTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankPairTutorialJudgementRepositoryBridge implements UseRankPairTutorialJudgementRepository {

    private final UseRankPairTutorialJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankPairTutorialJudgementRepositoryBridge(UseRankPairTutorialJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankPairTutorialJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankPairTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankPairTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankPairTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankPairTutorialJudgement save(UseRankPairTutorialJudgement judgement) {
        return judgementRepository.save(judgement);
    }



}
