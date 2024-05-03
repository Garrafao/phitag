package de.garrafao.phitag.domain.tutorial.userankpair;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankPairTutorialJudgementRepository {

    List<UseRankPairTutorialJudgement> findByQuery(final Query query);

    Page<UseRankPairTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankPairTutorialJudgement save(UseRankPairTutorialJudgement judgement);


}
