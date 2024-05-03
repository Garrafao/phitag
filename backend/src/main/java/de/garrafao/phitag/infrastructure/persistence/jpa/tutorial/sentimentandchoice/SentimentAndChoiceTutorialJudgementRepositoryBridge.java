package de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.sentimentandchoice;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgement;
import de.garrafao.phitag.domain.tutorial.sentimentandchoice.SentimentAndChoiceTutorialJudgementRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.tutorial.sentimentandchoice.query.SentimentAndChoiceTutorialJudgementQueryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SentimentAndChoiceTutorialJudgementRepositoryBridge implements SentimentAndChoiceTutorialJudgementRepository {

    private final SentimentAndChoiceTutorialJudgementRepositoryJpa sentimentAndChoiceTutorialJudgementRepositoryJpa;

    @Autowired
    public SentimentAndChoiceTutorialJudgementRepositoryBridge(SentimentAndChoiceTutorialJudgementRepositoryJpa sentimentAndChoiceTutorialJudgementRepositoryJpa) {
        this.sentimentAndChoiceTutorialJudgementRepositoryJpa = sentimentAndChoiceTutorialJudgementRepositoryJpa;
    }

    @Override
    public List<SentimentAndChoiceTutorialJudgement> findByQuery(Query query) {
        return this.sentimentAndChoiceTutorialJudgementRepositoryJpa.findAll(new SentimentAndChoiceTutorialJudgementQueryJpa(query));
    }

    @Override
    public Page<SentimentAndChoiceTutorialJudgement> findByQueryPaged(Query query, PageRequestWraper page) {
        return this.sentimentAndChoiceTutorialJudgementRepositoryJpa.findAll(new SentimentAndChoiceTutorialJudgementQueryJpa(query), page.getPageRequest());
    }

    @Override
    public SentimentAndChoiceTutorialJudgement save(SentimentAndChoiceTutorialJudgement judgement) {
        return this.sentimentAndChoiceTutorialJudgementRepositoryJpa.save(judgement);
    }

    @Override
    public void delete(SentimentAndChoiceTutorialJudgement judgement) {
        this.sentimentAndChoiceTutorialJudgementRepositoryJpa.delete(judgement);
    }

}
