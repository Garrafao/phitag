package de.garrafao.phitag.domain.judgement.userankpairjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankPairJudgementRepository {

    List<UseRankPairJudgement> findByQuery(final Query query);

    Page<UseRankPairJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankPairJudgement save(UseRankPairJudgement judgement);

    void delete(UseRankPairJudgement judgement);

}
