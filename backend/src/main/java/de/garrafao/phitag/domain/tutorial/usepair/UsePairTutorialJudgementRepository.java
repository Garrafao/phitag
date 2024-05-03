package de.garrafao.phitag.domain.tutorial.usepair;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsePairTutorialJudgementRepository {

    List<UsePairTutorialJudgement> findByQuery(final Query query);

    Page<UsePairTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    UsePairTutorialJudgement save(UsePairTutorialJudgement judgement);

    void delete(UsePairTutorialJudgement judgement);


}
