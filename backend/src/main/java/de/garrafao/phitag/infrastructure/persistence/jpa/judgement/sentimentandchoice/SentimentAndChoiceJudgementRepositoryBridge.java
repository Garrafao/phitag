package de.garrafao.phitag.infrastructure.persistence.jpa.judgement.sentimentandchoice;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgement;
import de.garrafao.phitag.domain.judgement.sentimentandchoice.SentimentAndChoiceJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.judgement.sentimentandchoice.query.SentimentAndChoiceJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SentimentAndChoiceJudgementRepositoryBridge implements SentimentAndChoiceJudgementRepository {

    private final SentimentAndChoiceJudgementRepositoryJpa sentimentAndChoiceJudgementRepositoryJpa;

    @Autowired
    public SentimentAndChoiceJudgementRepositoryBridge(SentimentAndChoiceJudgementRepositoryJpa sentimentAndChoiceJudgementRepositoryJpa) {
        this.sentimentAndChoiceJudgementRepositoryJpa = sentimentAndChoiceJudgementRepositoryJpa;
    }

    @Override
    public List<SentimentAndChoiceJudgement> findByQuery(Query query) {
        return this.sentimentAndChoiceJudgementRepositoryJpa.findAll(new SentimentAndChoiceJudgementQueryJpa(query));
    }

    @Override
    public Page<SentimentAndChoiceJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.sentimentAndChoiceJudgementRepositoryJpa.findAll(new SentimentAndChoiceJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public SentimentAndChoiceJudgement save(SentimentAndChoiceJudgement judgement) {
        return this.sentimentAndChoiceJudgementRepositoryJpa.save(judgement);
    }

    @Override
    public void delete(SentimentAndChoiceJudgement judgement) {
        this.sentimentAndChoiceJudgementRepositoryJpa.delete(judgement);
    }

    @Override
    public void batchDelete(Iterable<SentimentAndChoiceJudgement> judgements) {

        this.sentimentAndChoiceJudgementRepositoryJpa.deleteInBatch(judgements);
    }

}
