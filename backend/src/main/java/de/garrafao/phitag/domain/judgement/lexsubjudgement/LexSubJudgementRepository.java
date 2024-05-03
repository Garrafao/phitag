package de.garrafao.phitag.domain.judgement.lexsubjudgement;

import java.util.List;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface LexSubJudgementRepository {

    List<LexSubJudgement> findByQuery(final Query query);

    Page<LexSubJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    LexSubJudgement save(LexSubJudgement judgement);

    void delete(LexSubJudgement judgement);

    void batchDelete(Iterable<LexSubJudgement> judgements);
}
