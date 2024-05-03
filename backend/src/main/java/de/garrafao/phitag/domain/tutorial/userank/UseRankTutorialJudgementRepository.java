package de.garrafao.phitag.domain.tutorial.userank;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UseRankTutorialJudgementRepository {

    List<UseRankTutorialJudgement> findByQuery(final Query query);

    Page<UseRankTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UseRankTutorialJudgement save(UseRankTutorialJudgement judgement);

    void delete(UseRankTutorialJudgement judgement);



}
