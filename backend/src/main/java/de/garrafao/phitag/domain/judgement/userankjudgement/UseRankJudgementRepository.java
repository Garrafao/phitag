package de.garrafao.phitag.domain.judgement.userankjudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.usepairjudgement.UsePairJudgement;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankJudgementRepository {

    List<UseRankJudgement> findByQuery(final Query query);

    Page<UseRankJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankJudgement save(UseRankJudgement judgement);

    void delete(UseRankJudgement judgement);

    void batchDelete(Iterable<UseRankJudgement> judgements);

}
