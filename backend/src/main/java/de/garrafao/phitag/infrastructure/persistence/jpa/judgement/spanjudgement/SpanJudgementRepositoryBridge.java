package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.spanjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgement;
import de.garrafao.phitag.domain.judgement.spanjudgement.SpanJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.spanjudgement.query.SpanJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SpanJudgementRepositoryBridge implements SpanJudgementRepository {

    private final SpanJudgementRepositoryJpa spanJudgementRepositoryJpa;

    @Autowired
    public SpanJudgementRepositoryBridge(SpanJudgementRepositoryJpa spanJudgementRepositoryJpa) {
        this.spanJudgementRepositoryJpa = spanJudgementRepositoryJpa;
    }

    @Override
    public List<SpanJudgement> findByQuery(Query query) {
        return this.spanJudgementRepositoryJpa.findAll(new SpanJudgementQueryJpa(query));
    }

    @Override
    public Page<SpanJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.spanJudgementRepositoryJpa.findAll(new SpanJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public SpanJudgement save(SpanJudgement judgement) {
        return this.spanJudgementRepositoryJpa.save(judgement);
    }

    @Override
    public void delete(SpanJudgement judgement) {
        this.spanJudgementRepositoryJpa.delete(judgement);
    }

    @Override
    public void batchDelete(Iterable<SpanJudgement> judgements) {

        this.spanJudgementRepositoryJpa.deleteInBatch(judgements);
    }

}
