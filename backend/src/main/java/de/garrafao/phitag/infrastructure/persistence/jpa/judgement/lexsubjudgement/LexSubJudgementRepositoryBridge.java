package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.lexsubjudgement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgement;
import de.garrafao.phitag.domain.judgement.lexsubjudgement.LexSubJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.lexsubjudgement.query.LexSubJudgementQueryJpa;

@Repository
public class LexSubJudgementRepositoryBridge implements LexSubJudgementRepository {

    private final LexSubJudgementRepositoryJpa lexSubJudgementRepositoryJpa;

    @Autowired
    public LexSubJudgementRepositoryBridge(LexSubJudgementRepositoryJpa lexSubJudgementRepositoryJpa) {
        this.lexSubJudgementRepositoryJpa = lexSubJudgementRepositoryJpa;
    }

    @Override
    public List<LexSubJudgement> findByQuery(Query query) {
        return this.lexSubJudgementRepositoryJpa.findAll(new LexSubJudgementQueryJpa(query));
    }

    @Override
    public Page<LexSubJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.lexSubJudgementRepositoryJpa.findAll(new LexSubJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public LexSubJudgement save(LexSubJudgement judgement) {
        return this.lexSubJudgementRepositoryJpa.save(judgement);
    }

    @Override
    public void delete(LexSubJudgement judgement) {
        this.lexSubJudgementRepositoryJpa.delete(judgement);
    }

    @Override
    public void batchDelete(Iterable<LexSubJudgement> judgements) {

        this.lexSubJudgementRepositoryJpa.deleteInBatch(judgements);
    }

}
