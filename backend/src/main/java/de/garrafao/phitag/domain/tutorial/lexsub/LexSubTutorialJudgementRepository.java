package de.garrafao.phitag.domain.tutorial.lexsub;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LexSubTutorialJudgementRepository {

    List<LexSubTutorialJudgement> findByQuery(final Query query);

    Page<LexSubTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    LexSubTutorialJudgement save(LexSubTutorialJudgement judgement);

    void delete(LexSubTutorialJudgement judgement);
}
