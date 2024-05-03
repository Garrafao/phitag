package de.garrafao.phitag.domain.judgement.userankrelativejudgement;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankRelativeJudgementRepository {

    List<UseRankRelativeJudgement> findByQuery(final Query query);

    Page<UseRankRelativeJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankRelativeJudgement save(UseRankRelativeJudgement judgement);

    void delete(UseRankRelativeJudgement judgement);

}
