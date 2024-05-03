package de.garrafao.phitag.domain.judgement.usepairjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsePairJudgementRepository {

    List<UsePairJudgement> findByQuery(final Query query);

    Page<UsePairJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UsePairJudgement save(UsePairJudgement judgement);

    void delete(UsePairJudgement judgement);

    void batchDelete(Iterable<UsePairJudgement> judgements);


}
