package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userank;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.userank.UseRankTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.userank.query.UseRankTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankTutorialJudgementRepositoryBridge implements UseRankTutorialJudgementRepository {

    private final UseRankTutorialJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankTutorialJudgementRepositoryBridge(UseRankTutorialJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankTutorialJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankTutorialJudgement save(UseRankTutorialJudgement judgement) {
        return judgementRepository.save(judgement);
    }

    @Override
    public void delete(UseRankTutorialJudgement judgement) {
        judgementRepository.delete(judgement);
    }



}
