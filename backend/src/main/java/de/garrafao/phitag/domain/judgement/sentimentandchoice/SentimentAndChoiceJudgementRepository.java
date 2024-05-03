package de.garrafao.phitag.domain.judgement.sentimentandchoice;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SentimentAndChoiceJudgementRepository {

    List<SentimentAndChoiceJudgement> findByQuery(final Query query);

    Page<SentimentAndChoiceJudgement> findByQueryPaged(final Query query, final PageRequestWraper page);

    SentimentAndChoiceJudgement save(SentimentAndChoiceJudgement judgement);

    void delete(SentimentAndChoiceJudgement judgement);

    void batchDelete(Iterable<SentimentAndChoiceJudgement> judgements);
}
