package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankpairjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgement;
import de.garrafao.phitag.domain.judgement.userankpairjudgement.UseRankPairJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankpairjudgement.query.UseRankPairJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankPairJudgementRepositoryBridge implements UseRankPairJudgementRepository {

    private final UseRankPairJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankPairJudgementRepositoryBridge(UseRankPairJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankPairJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankPairJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankPairJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankPairJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankPairJudgement save(UseRankPairJudgement judgement) {
        return judgementRepository.save(judgement);
    }

    @Override
    public void delete(UseRankPairJudgement judgement) {
        judgementRepository.delete(judgement);
    }



}
