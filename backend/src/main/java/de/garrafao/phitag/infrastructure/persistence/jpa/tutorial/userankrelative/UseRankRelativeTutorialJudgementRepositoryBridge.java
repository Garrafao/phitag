package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankrelative;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userankrelative.UseRankRelativeTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userankrelative.query.UseRankRelativeTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankRelativeTutorialJudgementRepositoryBridge implements UseRankRelativeTutorialJudgementRepository {

    private final UseRankRelativeTutorialJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankRelativeTutorialJudgementRepositoryBridge(UseRankRelativeTutorialJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankRelativeTutorialJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankRelativeTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankRelativeTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankRelativeTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankRelativeTutorialJudgement save(UseRankRelativeTutorialJudgement judgement) {
        return judgementRepository.save(judgement);
    }



}
