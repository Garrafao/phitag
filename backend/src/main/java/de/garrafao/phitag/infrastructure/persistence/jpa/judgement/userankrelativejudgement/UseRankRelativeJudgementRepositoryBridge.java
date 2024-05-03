package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankrelativejudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgement;
import de.garrafao.phitag.domain.judgement.userankrelativejudgement.UseRankRelativeJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankrelativejudgement.query.UseRankRelativeJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankRelativeJudgementRepositoryBridge implements UseRankRelativeJudgementRepository {

    private final UseRankRelativeJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankRelativeJudgementRepositoryBridge(UseRankRelativeJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankRelativeJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankRelativeJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankRelativeJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankRelativeJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankRelativeJudgement save(UseRankRelativeJudgement judgement) {
        return judgementRepository.save(judgement);
    }

    @Override
    public void delete(UseRankRelativeJudgement judgement) {
        judgementRepository.delete(judgement);
    }



}
