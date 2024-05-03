package de.garrafao.phitag.domain.tutorial.userankrelative;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankRelativeTutorialJudgementRepository {

    List<UseRankRelativeTutorialJudgement> findByQuery(final Query query);

    Page<UseRankRelativeTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankRelativeTutorialJudgement save(UseRankRelativeTutorialJudgement judgement);

}
