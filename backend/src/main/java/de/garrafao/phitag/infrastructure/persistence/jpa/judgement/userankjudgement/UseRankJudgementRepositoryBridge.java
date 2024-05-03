package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankjudgement;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgement;
import de.garrafao.phitag.domain.judgement.userankjudgement.UseRankJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.userankjudgement.query.UseRankJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UseRankJudgementRepositoryBridge implements UseRankJudgementRepository {

    private final UseRankJudgementRepositoryJpa judgementRepository;

    @Autowired
    public UseRankJudgementRepositoryBridge(UseRankJudgementRepositoryJpa judgementRepository) {
        this.judgementRepository = judgementRepository;
    }

    @Override
    public List<UseRankJudgement> findByQuery(Query query) {
        return judgementRepository.findAll(new UseRankJudgementQueryJpa(query));
    }

    @Override
    public Page<UseRankJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return judgementRepository.findAll(new UseRankJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public UseRankJudgement save(UseRankJudgement judgement) {
        return judgementRepository.save(judgement);
    }

    @Override
    public void delete(UseRankJudgement judgement) {
        judgementRepository.delete(judgement);
    }

    @Override
    public void batchDelete(Iterable<UseRankJudgement> judgements) {
        this.judgementRepository.deleteInBatch(judgements);
    }


}
