package de.garrafao.phitag.domain.judgement.spanjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpanJudgementRepository {

    List<SpanJudgement> findByQuery(final Query query);

    Page<SpanJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    SpanJudgement save(SpanJudgement judgement);

    void delete(SpanJudgement judgement);

    void batchDelete(Iterable<SpanJudgement> judgements);
}
