package de.garrafao.phitag.domain.tutorial.sentimentandchoice;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SentimentAndChoiceTutorialJudgementRepository {

    List<SentimentAndChoiceTutorialJudgement> findByQuery(final Query query);

    Page<SentimentAndChoiceTutorialJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    SentimentAndChoiceTutorialJudgement save(SentimentAndChoiceTutorialJudgement judgement);

    void delete(SentimentAndChoiceTutorialJudgement judgement);
}
